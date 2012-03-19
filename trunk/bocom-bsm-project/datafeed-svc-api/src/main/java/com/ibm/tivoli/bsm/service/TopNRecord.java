/**
 * 
 */
package com.ibm.tivoli.bsm.service;

/**
 * @author zhaodonglu
 *
 */
public class TopNRecord {
  
  private int rankSeq = 0;
  private String rankName = null;
  private double rankValue = 0.0;
  private String rankCode = null;

  /**
   * 
   */
  public TopNRecord() {
    super();
  }

  /**
   * @return the rankSeq
   */
  public int getRankSeq() {
    return rankSeq;
  }

  /**
   * @param rankSeq the rankSeq to set
   */
  public void setRankSeq(int rankSeq) {
    this.rankSeq = rankSeq;
  }

  /**
   * @return the rankName
   */
  public String getRankName() {
    return rankName;
  }

  /**
   * @param rankName the rankName to set
   */
  public void setRankName(String rankName) {
    this.rankName = rankName;
  }

  /**
   * @return the rankValue
   */
  public double getRankValue() {
    return rankValue;
  }

  /**
   * @param rankValue the rankValue to set
   */
  public void setRankValue(double rankValue) {
    this.rankValue = rankValue;
  }

  /**
   * @return the rankCode
   */
  public String getRankCode() {
    return rankCode;
  }

  /**
   * @param rankCode the rankCode to set
   */
  public void setRankCode(String rankCode) {
    this.rankCode = rankCode;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return String.format("TopNRecord [rankSeq=%s, rankName=%s, rankValue=%s, rankCode=%s]", rankSeq, rankName,
        rankValue, rankCode);
  }

}
