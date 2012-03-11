package com.npower.dm.bootstrap;

import com.npower.cp.OTAInventory;
import com.npower.dm.core.Carrier;
import com.npower.dm.core.Country;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Device;
import com.npower.dm.core.Subscriber;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.sms.SmsException;
import com.npower.sms.client.SmsSender;
import com.npower.wap.omacp.OMACPSecurityMethod;

public abstract class AbstractBootstrapService {

  private ManagementBeanFactory beanFactory = null;
  private OTAInventory otaInventory = null;
  private SmsSender smsSender = null;

  public AbstractBootstrapService() {
    super();
  }

  public ManagementBeanFactory getBeanFactory() {
    return beanFactory;
  }

  public void setBeanFactory(ManagementBeanFactory beanFactory) {
    this.beanFactory = beanFactory;
  }

  public OTAInventory getOtaInventory() {
    return otaInventory;
  }

  public void setOtaInventory(OTAInventory otaInventory) {
    this.otaInventory = otaInventory;
  }

  public SmsSender getSmsSender() {
    return smsSender;
  }

  public void setSmsSender(SmsSender smsSender) {
    this.smsSender = smsSender;
  }

  public long bootstrap(String deviceExternalID, OMACPSecurityMethod pinType, String pin) throws DMException, SmsException {
    return this.bootstrap(deviceExternalID, pinType, pin, System.currentTimeMillis(), -1, -1);
  }

  public long bootstrap(String msisdn, String serverPassword, String serverNonce, String clientUsername, String clientPassword, String clientNonce, OMACPSecurityMethod pinType,
      String pin) throws DMException, SmsException {
    return this.bootstrap(msisdn, serverPassword, serverNonce, clientUsername, clientPassword, clientNonce, pinType, pin, System.currentTimeMillis(), -1, -1);
  }

  public abstract long bootstrap(String deviceExternalID, OMACPSecurityMethod pinType, String pin, long scheduleTime, int maxRetry, long maxDuration)
      throws DMException, SmsException;

  public abstract long bootstrap(String msisdn, String serverPassword, String serverNonce, String clientUsername, String clientPassword, String clientNonce,
      OMACPSecurityMethod pinType, String pin, long scheduleTime, int maxRetry, long maxDuration) throws DMException, SmsException;

  /**
   * Caculate PhoneNumber based Country Policy.
   * @param device
   * @return
   */
  public static String caculatePhoneNumber(Device device) {
    Subscriber subscriber = device.getSubscriber();
    String msisdn = subscriber.getPhoneNumber();
    Carrier carrier = subscriber.getCarrier();
    Country country = carrier.getCountry();
    if (country.getDisplayCountryCode()) {
       String countryCode = country.getCountryCode();
       if (countryCode != null && !msisdn.startsWith(countryCode)) {
          msisdn = countryCode + msisdn;
       }
    }
    return msisdn;
  }

}