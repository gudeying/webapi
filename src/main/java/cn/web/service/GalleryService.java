package cn.web.service;

import cn.web.model.Gallery;
import cn.web.sqldeal.GalleryDeal;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("GalleryService")
public class GalleryService {
	@Autowired
	public GalleryDeal deal;

	public PageInfo<Gallery> getPageGallery(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		PageInfo<Gallery> info = new PageInfo<Gallery>(this.deal.getAllGallery());
		return info;
	}

	public PageInfo<Gallery> getPageGalleryByUser(int pageNum, int pageSize, String username) {
		PageHelper.startPage(pageNum, pageSize);
		PageInfo<Gallery> info = new PageInfo<Gallery>(this.deal.get15GalleryByUser(username));
		return info;
	}

	public List<Gallery> getLast6Gallery() {
		return this.deal.get6Gallery();
	}

	public void save(Gallery g) {
		this.deal.save(g);
	}

	public Gallery getById(int id) {
		return this.deal.getById(id);
	}

	public GalleryDeal getDao() {
		return this.deal;
	}
}
