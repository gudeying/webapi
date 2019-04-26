package cn.web.service;

import cn.web.model.Blog;
import cn.web.sqldeal.BlogDeal;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("BlogService")
public class BlogService
{
  @Autowired
  public BlogDeal blogdeal;
  
  public List<Blog> getLast3Blogs()
  {
    return this.blogdeal.getLast3Blog();
  }
  
  public PageInfo<Blog> getPageBlog(int pageNum, int pageSize)
  {
    PageHelper.startPage(pageNum, pageSize);
    PageInfo<Blog> info = new PageInfo(this.blogdeal.getAllBlogs());
    return info;
  }
  
  public PageInfo<Blog> getPageBlogByUser(int pageNum, int pageSize, String username)
  {
    PageHelper.startPage(pageNum, pageSize);
    PageInfo<Blog> info = new PageInfo(this.blogdeal.getBlogByUser(username));
    return info;
  }
  
  public void save(Blog blog)
  {
    this.blogdeal.save(blog);
  }
  
  public void deleteById(int id)
  {
    this.blogdeal.deleteBlogById(id);
  }
  
  public Blog getBlogById(int id)
  {
    return this.blogdeal.getBlogById(id);
  }
}
