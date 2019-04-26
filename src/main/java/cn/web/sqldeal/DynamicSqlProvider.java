package cn.web.sqldeal;

import java.util.List;

import org.apache.ibatis.jdbc.SQL;

import cn.web.model.Article;
import cn.web.model.User;
import cn.web.restapi.DataModel;

public class DynamicSqlProvider {
	public String updateUserSql(User user) {
		return new SQL() {
			{
				UPDATE("user");
				if (0 != user.getAge()) {
					SET("age=#{age}");
				}
				if (0 != user.getLevel()) {
					SET("level=#{level}");
				}
				if (null != user.getAutograph()) {
					SET("autograph=#{autograph}");
				}
				if (null != user.getMail()) {
					SET("mail=#{main}");
				}
				if (null != user.getName()) {
					SET("name=#{name}");
				}
				if (null != user.getPasw()) {
					SET("pasw=#{pasw}");
				}
				if (null != user.getSex()) {
					SET("sex=#{sex}");
				}
				if (null != user.getUserlogo()) {
					SET("userlogo=#{userlogo}");
				}
				if (null != user.getNickName()) {
					SET("nickname=#{nickname}");
				}
			}
		}.toString();
	}

	public String test(Article article) {
		return new SQL() {
			{
				SELECT("id", "title", "author", "src", "description", "subject", "time", "snum", "znum", "authorid ");
				FROM("article");
				WHERE("1=1");
				if (article.getAuthor() != null) {
					WHERE("author=#{author}");
				}
				if (article.getSubject() != null) {
					WHERE("subject = #{subject}");
				}
				if (article.getTitle() != null) {
					WHERE("title like #{title}");
				}
			}
		}.toString();
	}

	public String getArticleList(DataModel dataModel) {
		StringBuilder builder = new StringBuilder();
		builder.append("SELECT id, title, author, src, description, subject, time, snum, znum, authorid from ")
				.append(dataModel.getTable()).append(" ").append(dataModel.where()).append(dataModel.orderBy());

		return builder.toString();
	}

	public static void main(String[] args) {
		Article article = new Article();
			article.setAuthor("kavin");
			DynamicSqlProvider provider = new DynamicSqlProvider();
			String sql = provider.test(article);
			System.out.println(sql);
	}
}
