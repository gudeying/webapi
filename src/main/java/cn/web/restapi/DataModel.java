package cn.web.restapi;

import java.util.ArrayList;
import java.util.List;

public class DataModel {
	/**
	 * 查哪个表
	 */
	private String table = "ARTICLE";
	/**
	 * 根据哪个字段排序
	 */
	private String sortBy;
	/**
	 * 每页多少
	 */
	private int pageSize;
	/**
	 * 请求的页码
	 */
	private int pageNum;
	/**
	 * 正序或者反序
	 */
	private int sortWay;
	/**
	 * 每页多少条记录
	 */
	private int rowsPerPage;
	/**
	 * where语句的字段
	 */
	private List<String> filter;

	public void addFilterParam(String condition) {
		if (filter == null) {
			filter = new ArrayList<>();
		}
		this.filter.add(condition);
	}

	public String where() {
		if (this.filter.isEmpty()) {
			return "1=1";
		}
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < filter.size(); i++) {
			String condition = filter.get(i);
			if (i == 0) {
				stringBuilder.append("where ").append(condition).append(" ");
			} else {
				stringBuilder.append(" AND ").append(condition).append(" ");
			}

		}
		return stringBuilder.toString();
	}

	public String orderBy() {
		StringBuilder builder = new StringBuilder(" order by ");
		if (this.sortBy != null) {
			builder.append(sortBy).append(" ");
		} else {
			builder.append("ID").append(" ");
		}
		if (sortWay != 0) {
			builder.append("DESC");
		} else {
			builder.append("");
		}
		return builder.toString();
	}
	
	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	

	public int getRowsPerPage() {
		return rowsPerPage;
	}

	public void setRowsPerPage(int rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}

	public String getTable() {
		if (this.table == null) {
			throw new RuntimeException();
		}
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public List<String> getFilter() {
		return filter;
	}

	public void setFilter(List<String> filter) {
		this.filter = filter;
	}

	public int getSortWay() {
		return sortWay;
	}

	public void setSortWay(int sortWay) {
		this.sortWay = sortWay;
	}

}
