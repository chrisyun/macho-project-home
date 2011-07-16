package com.ibm.tivoli.icbc.util;

import java.net.InetAddress;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class InetAddressConvertor  implements Converter {

    public boolean canConvert(Class clazz) {
      return clazz.equals(InetAddress.class);
    }

    public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {
        InetAddress inetAddr = (InetAddress) value;
        writer.startNode("encoded");
        writer.setValue(inetAddr.toString());
        writer.endNode();
    }

    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
      throw new RuntimeException("Un-support to unmarshal InetAddress: " + reader.getValue());
    }

  }