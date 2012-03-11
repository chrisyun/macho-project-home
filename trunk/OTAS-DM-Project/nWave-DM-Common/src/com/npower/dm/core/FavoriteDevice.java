package com.npower.dm.core;


public interface FavoriteDevice {

  public abstract Long getId();

  public abstract void setId(Long id);

  public abstract Favorite getFavorite();

  public abstract void setFavorite(Favorite favorite);

  public abstract Long getDeviceId();

  public abstract void setDeviceId(Long deviceId);

}