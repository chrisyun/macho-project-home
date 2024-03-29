package com.npower.dm.hibernate.entity;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;

import com.npower.dm.core.AutomaticProvisionJob;
import com.npower.dm.core.Carrier;
import com.npower.dm.management.selector.AutomaticProvisionJobSelector;

/**
 * AbstractAutoProvisionJob generated by MyEclipse - Hibernate Tools
 */

public abstract class AbstractAutoProvisionJob implements java.io.Serializable, AutomaticProvisionJob {

  // Fields

  private long                      ID                    = 0;

  private Carrier                   carrier;

  private String                    type;

  private String                    jobType;

  private String                    jobTypeForDisplay;

  private String                    state;

  private boolean                   running               = true;

  private String                    name;

  private String                    description;

  private Date                      beginTime;

  private Date                      endTime;

  private String                    criteria;

  private String                    script;
  
  //private AutomaticProvisionJobSelector jobSelector = null;

  private long                      changeVersion         = 0;

  private Set<AutoJobProfileConfig> autoJobProfileConfigs = new TreeSet<AutoJobProfileConfig>();

  private Set<AutoJobNodesDiscover> autoJobNodesDiscovers = new TreeSet<AutoJobNodesDiscover>();

  // Constructors

  /** default constructor */
  public AbstractAutoProvisionJob() {
  }

  /** minimal constructor */
  public AbstractAutoProvisionJob(long provReqId, String type, String jobType, String jobTypeForDisplay, String state,
      boolean running, Date beginTime, long changeVersion) {
    this.ID = provReqId;
    this.type = type;
    this.jobType = jobType;
    this.jobTypeForDisplay = jobTypeForDisplay;
    this.state = state;
    this.running = running;
    this.beginTime = beginTime;
    this.changeVersion = changeVersion;
  }

  // Property accessors

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.entity.AutoProvisionJob#getID()
   */
  public long getID() {
    return this.ID;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.entity.AutoProvisionJob#setID(java.lang.Long)
   */
  public void setID(long ID) {
    this.ID = ID;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.entity.AutoProvisionJob#getCarrier()
   */
  public Carrier getCarrier() {
    return this.carrier;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.entity.AutoProvisionJob#setCarrier(com.npower.dm.core.Carrier)
   */
  public void setCarrier(Carrier carrier) {
    this.carrier = carrier;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.entity.AutoProvisionJob#getType()
   */
  public String getType() {
    return this.type;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.entity.AutoProvisionJob#setType(java.lang.String)
   */
  public void setType(String type) {
    this.type = type;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.entity.AutoProvisionJob#getJobType()
   */
  public String getJobType() {
    return this.jobType;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.entity.AutoProvisionJob#setJobType(java.lang.String)
   */
  public void setJobType(String jobType) {
    this.jobType = jobType;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.entity.AutoProvisionJob#getJobTypeForDisplay()
   */
  public String getJobTypeForDisplay() {
    return this.jobTypeForDisplay;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.entity.AutoProvisionJob#setJobTypeForDisplay(java.lang.String)
   */
  public void setJobTypeForDisplay(String jobTypeForDisplay) {
    this.jobTypeForDisplay = jobTypeForDisplay;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.entity.AutoProvisionJob#getState()
   */
  public String getState() {
    return this.state;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.entity.AutoProvisionJob#setState(java.lang.String)
   */
  public void setState(String state) {
    this.state = state;
  }

  /**
   * @return
   */
  public boolean getRunning() {
    return this.running;
  }

  /**
   * @param running
   */
  public void setRunning(boolean running) {
    this.running = running;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.entity.AutoProvisionJob#getName()
   */
  public String getName() {
    return this.name;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.entity.AutoProvisionJob#setName(java.lang.String)
   */
  public void setName(String name) {
    this.name = name;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.entity.AutoProvisionJob#getDescription()
   */
  public String getDescription() {
    return this.description;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.entity.AutoProvisionJob#setDescription(java.lang.String)
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.AutomaticProvisionJob#getBeginTime()
   */
  public Date getBeginTime() {
    return this.beginTime;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.AutomaticProvisionJob#setBeginTime(java.util.Date)
   */
  public void setBeginTime(Date beginTime) {
    this.beginTime = beginTime;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.AutomaticProvisionJob#getEndTime()
   */
  public Date getEndTime() {
    return this.endTime;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.AutomaticProvisionJob#setEndTime(java.util.Date)
   */
  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.entity.AutoProvisionJob#getCriteria()
   */
  public String getCriteria() {
    return this.criteria;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.entity.AutoProvisionJob#setCriteria(java.lang.String)
   */
  public void setCriteria(String criteria) {
    this.criteria = criteria;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.entity.AutoProvisionJob#getScript()
   */
  public String getScript() {
    return this.script;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.entity.AutoProvisionJob#setScript(java.lang.String)
   */
  public void setScript(String script) {
    this.script = script;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.entity.AutoProvisionJob#getChangeVersion()
   */
  public long getChangeVersion() {
    return this.changeVersion;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.hibernate.entity.AutoProvisionJob#setChangeVersion(java.lang.Long)
   */
  public void setChangeVersion(long changeVersion) {
    this.changeVersion = changeVersion;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.AutomaticProvisionJob#getJobSelector()
   */
  public AutomaticProvisionJobSelector getJobSelector() throws IOException {
    if (StringUtils.isEmpty(this.getCriteria())) {
       return null;
    } else {
      ByteArrayInputStream in = new ByteArrayInputStream(this.getCriteria().getBytes());
      XMLDecoder decoder = new XMLDecoder(in);
      AutomaticProvisionJobSelector selector = (AutomaticProvisionJobSelector)decoder.readObject();
      in.close();
      return selector;
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.core.AutomaticProvisionJob#setJobSelector(com.npower.dm.management.AutomaticProvisionJobSelector)
   */
  public void setJobSelector(AutomaticProvisionJobSelector jobSelector) throws IOException {
    if (jobSelector == null) {
       this.setCriteria(null);
    } else {
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      XMLEncoder encoder = new XMLEncoder(out);
      encoder.writeObject(jobSelector);
      encoder.flush();
      encoder.close();
      this.setCriteria(out.toString());
    }
  }

  /**
   * @return the autoJobNodesDiscovers
   */
  public Set<AutoJobNodesDiscover> getAutoJobNodesDiscovers() {
    return autoJobNodesDiscovers;
  }

  /**
   * @param autoJobNodesDiscovers
   *          the autoJobNodesDiscovers to set
   */
  public void setAutoJobNodesDiscovers(Set<AutoJobNodesDiscover> autoJobNodesDiscovers) {
    this.autoJobNodesDiscovers = autoJobNodesDiscovers;
  }

  /**
   * @return the autoJobProfileConfigs
   */
  public Set<AutoJobProfileConfig> getAutoJobProfileConfigs() {
    return autoJobProfileConfigs;
  }

  /**
   * @param autoJobProfileConfigs
   *          the autoJobProfileConfigs to set
   */
  public void setAutoJobProfileConfigs(Set<AutoJobProfileConfig> autoJobProfileConfigs) {
    this.autoJobProfileConfigs = autoJobProfileConfigs;
  }

}