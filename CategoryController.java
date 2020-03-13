
@Controller
@RequestMapping("")
public class CategoryController{
    @Autowired
    CategoryService categoryService;
    @RequestMapping("admin_category_list")
    public String list(Model model,Page page){
        List<Category> cs = categoryService.list(page);
        int total = categoryService.total();
        page.setTotal(total);
        model.addAttribute("cs",cs);
        model.addAttribute("page",page);
        return "admin/listCategory";
    }
    @RequestMapping("admin_category_add")
    public String add(Category c,HttpSession session,UploadedImageFile uploadedImageFile) throws IOException{
        categoryService.add(c);
        File imageFolder = new File(session.getServletContext().getRealPath("img/category"));
        File file = new File(imageFolder,c.getId()+".jpg");
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        uploadedImageFile.getImage().transferTo(file);
        BufferedImage img = ImageUtil.change2jpg(file);
        ImageIO.write(img,"jpg",file);
        return "redirect:/admin_category_list";
    }
    @RequestMapping("admin_category_delete")
    public String delete(int id,HttpSession session) throws IOException{
        categoryService.delete(id);
        File imageFolder = new File(session.getServletContext().getRealPath("img/category"));
        File file = new File(imageFolder,id+".jpg");
        file.delete();
        return "redirect:/admin_category_list";
    }
    @RequestMapping("admin_category_edit")
    public String edit(int id,Model model) throws IOException{
        Category c = categoryService.get(id);
        model.addAttribute("c",c);
        return "admin/editCategory";
    }
    @RequestMapping("admin_category_update")
    public String update(Category c,HttpSession session, UploadedImageFile uploadedImageFile) throws IOException{
        categoryService.update(c);
        MultipartFile image = uploadedImageFile.getImage();
        if(null!=image && !image.isEmpty()){
            File imageFolder = new File(session.getServletContext().getRealPath("img/category"));
            File file = new File(imageFolder, c.getId()+".jpg");
            image.transferTo(file);
            BufferedImage img = ImageUtil.change2jpg(file);
            ImageIO.write(img,"jpg",file);
        }
        return "redirect:/admin_category_list";
    }
}