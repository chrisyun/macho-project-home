package com.npower.dm.hibernate.management;

import java.util.List;

import com.npower.dm.management.PaginatedResult;

public abstract class AbstractPaginatedResult implements PaginatedResult {

  protected int pageNumber = 1;
  protected int objectsPerPage = 20;
  protected List<?> results = null;
  protected int fullListSize;

  protected AbstractPaginatedResult() {
    super();
  }

  /**
   * @param criteria
   * @param pageNumber
   * @param objectsPerPage
   */
  public AbstractPaginatedResult(int pageNumber, int objectsPerPage) {
    super();
    this.pageNumber = pageNumber;
    this.objectsPerPage = objectsPerPage;
  }

  /**
   * @param objectsPerPage
   *          the objectsPerPage to set
   */
  public void setObjectsPerPage(int objectsPerPage) {
    this.objectsPerPage = objectsPerPage;
  }

  /**
   * @param pageNumber
   *          the pageNumber to set
   */
  public void setPageNumber(int pageNumber) {
    this.pageNumber = pageNumber;
  }

  public int getFullListSize() {
    return this.fullListSize;
  }

  public List<?> getList() {
    return this.results;
  }

  public int getObjectsPerPage() {
    return this.objectsPerPage;
  }

  public int getPageNumber() {
    return this.pageNumber;
  }

  public String getSearchId() {
    // TODO Auto-generated method stub
    return null;
  }

  public String getSortCriterion() {
    // TODO Auto-generated method stub
    return null;
  }

  /**
   * Load data
   */
  protected abstract void load();
}