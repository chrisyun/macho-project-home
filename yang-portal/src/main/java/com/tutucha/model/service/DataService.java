package com.tutucha.model.service;

import java.util.List;

import com.tutucha.model.entity.Area;
import com.tutucha.model.entity.Navigation;
import com.tutucha.model.entity.NavigationCategory;

public interface DataService {

  public abstract Area getAreaById(String pageId, String areaId);
  
  public abstract List<Navigation> getNavigationsByCategory(String categoryId);
  
  public abstract NavigationCategory getCategoryById(String categoryId);
  
  public abstract Navigation getNavigationById(String id);

}
