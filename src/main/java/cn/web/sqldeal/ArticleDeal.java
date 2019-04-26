package cn.web.sqldeal;

import cn.web.model.Article;
import cn.web.model.ArticleHead;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Mapper
public abstract interface ArticleDeal {
    @Transactional(readOnly = true)
    @Select({"select * from article where id = #{id}"})
    public abstract Article getArticleById(int paramInt);

    @Select({"select * from article order by id desc"})
    public abstract List<Article> getAllArticles();

    @Select({"select * from article where title like #{liketitle} order by id desc"})
    public abstract List<Article> getLikeTitle(String paramString);

    @Select({"select * from article order by id desc limit 0,6"})
    public abstract List<Article> getLast3articles();

    @Select({"select * from article where subject = #{subject} order by id desc"})
    public abstract List<Article> getLastArticlesBySubject(String paramString);

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    @Delete({"delete  from article where id = #{id}"})
    public abstract void deleteArticleById(int paramInt);

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    @Insert({"insert into article(author,title,content,src,description,subject,time,authorid) values(#{author},#{title},#{content},#{src},#{description},#{subject},#{time},#{authorid})"})
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    public abstract void save(Article paramArticle);

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    @Insert({"insert into article(author,title,content,src,description,subject,time,authorid) values(#{author},#{title},#{content},#{src},#{description},#{subject},#{time},#{authorid})"})
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    public abstract int saveAndReturn(Article paramArticle);

    @Select({"select * from article where author = #{username} order by id desc"})
    public abstract List<Article> getArticleByUser(String paramString);

    @Select({"select * from article where subject = #{subject} and id > #{id} order by id desc limit 0,1"})
    public abstract Article getNextBySubject(@Param("subject") String paramString, @Param("id") int paramInt);

    @Select({"select * from article where subject = #{subject} and id < #{id} order by id desc limit 0,1"})
    public abstract Article getPreviousBySubject(@Param("subject") String paramString, @Param("id") int paramInt);

    @Select({"select * from article where author = #{author} and subject = #{subject} order by id desc"})
    public abstract List<Article> getBySubjectAuthor(@Param("author") String paramString1, @Param("subject") String paramString2);
    
    @Select({"select * from article where authorid = #{authorid} and subject = #{subject} order by snum"})
    public abstract List<Article> getBySubjectAuthorId(@Param("authorid") int paramString1, @Param("subject") String paramString2);
    
    
    @Update({"update article set snum=snum+1 where id=#{id}"})
    public abstract void shoucang(int paramInt);

    @Update({"update article set snum=snum-1 where id=#{id}"})
    public abstract void quxiao(int paramInt);

    @Select({"select * from article order by snum desc limit 0,6"})
    public abstract List<Article> getHot();

    @Select({"select * from article where authorid=#{userid} order by snum desc limit 0,6"})
    public abstract List<Article> getUserHot(int paramInt);

    @Select({"select * from article where authorid=#{authorid} order by snum desc limit 0,6"})
    public abstract List<Article> getUserHotByAuthorId(int paramInt);

    @Select({"select * from article where authorid = #{userid} order by id desc"})
    public abstract List<Article> getArticleByUserId(int paramInt);

    @Select({"select * from article where author=#{author} and time = #{time} and title=#{title}"})
    public abstract Article getLocal(@Param("author") String paramString1, @Param("time") String paramString2, @Param("title") String paramString3);

    @Select({"select distinct subject from article"})
    public abstract List<String> getTopics();

    @Select("select snum from article where id=#{articleId}")
    public int getStarNum(int articleId);
    
    //
    @Select("select id,title,author,src,description,subject,time,snum,znum,authorid from article order by id desc")
    public List<ArticleHead> getArticleHeadList();
    @Select("select id,title,author,src,description,subject,time,snum,znum,authorid from article where subject=#{topic} order by id desc")
    public List<ArticleHead> getTopicArticleHeadList(String topic);
    @Select("select id,title,author,src,description,subject,time,snum,znum,authorid from article where authorid =#{authorId} and subject=#{topic} order by id desc")
    public List<ArticleHead> getUserTopicArticleHeadList(@Param("topic")String topic,@Param("authorId")int authorId);
    @Select("select id,title,author,src,description,subject,time,snum,znum,authorid from article where authorid =#{authorId} order by id desc")
    public List<ArticleHead> getUserArticleHeadList(@Param("authorId")int authorId);
    
    @Select("select id,title,author,src,description,subject,time,snum,znum,authorid from article where title like #{keyWord} order by id desc")
    public List<ArticleHead> searchList(@Param("keyWord")String keyWord);
}
