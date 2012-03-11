package com.npower.dm.core;

import java.util.Set;

public interface Favorite {

  public abstract Long getFavoriteId();

  public abstract void setFavoriteId(Long favoriteId);

  public abstract String getName();

  public abstract void setName(String name);

  public abstract String getDescription();

  public abstract void setDescription(String description);

  public abstract boolean isShared();

  public abstract void setShared(boolean isshare);

  public abstract String getOwner();

  public abstract void setOwner(String owner);

  public abstract Set<FavoriteDevice> getFavoriteDevices();

  public abstract void setFavoriteDevices(Set<FavoriteDevice> FavoriteDevices);

}