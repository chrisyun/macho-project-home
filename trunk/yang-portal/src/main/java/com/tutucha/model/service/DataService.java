package com.tutucha.model.service;

import java.util.List;

import com.tutucha.model.entity.Area;
import com.tutucha.model.entity.Game;
import com.tutucha.model.entity.Navigation;
import com.tutucha.model.entity.NavigationCategory;
import com.tutucha.model.entity.Software;
import com.tutucha.model.entity.Video;

public interface DataService {

  public abstract Area getAreaById(String pageId, String areaId);
  
  public abstract List<Navigation> getNavigationsByCategory(String categoryId);
  
  public abstract NavigationCategory getCategoryById(String categoryId);
  
  public abstract Navigation getNavigationById(String id);
  
  public abstract Game getGameById(String id);
  
  public abstract Software getSoftwareById(String id);
  
  public abstract Video getVideoById(String id);

}
