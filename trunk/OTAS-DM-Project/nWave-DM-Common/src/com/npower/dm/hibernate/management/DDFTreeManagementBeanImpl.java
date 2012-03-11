/**
 * $Header: /home/master/nWave-DM-Common/src/com/npower/dm/hibernate/management/DDFTreeManagementBeanImpl.java,v 1.15 2008/12/15 03:24:14 zhao Exp $
 * $Revision: 1.15 $
 * $Date: 2008/12/15 03:24:14 $
 *
 * ===============================================================================================
 * License, Version 1.1
 *
 * Copyright (c) 1994-2006 NPower Network Software Ltd.  All rights reserved.
 *
 * This SOURCE CODE FILE, which has been provided by NPower as part
 * of a NPower product for use ONLY by licensed users of the product,
 * includes CONFIDENTIAL and PROPRIETARY information of NPower.
 *
 * USE OF THIS SOFTWARE IS GOVERNED BY THE TERMS AND CONDITIONS
 * OF THE LICENSE STATEMENT AND LIMITED WARRANTY FURNISHED WITH
 * THE PRODUCT.
 *
 * IN PARTICULAR, YOU WILL INDEMNIFY AND HOLD NPower, ITS RELATED
 * COMPANIES AND ITS SUPPLIERS, HARMLESS FROM AND AGAINST ANY CLAIMS
 * OR LIABILITIES ARISING OUT OF THE USE, REPRODUCTION, OR DISTRIBUTION
 * OF YOUR PROGRAMS, INCLUDING ANY CLAIMS OR LIABILITIES ARISING OUT OF
 * OR RESULTING FROM THE USE, MODIFICATION, OR DISTRIBUTION OF PROGRAMS
 * OR FILES CREATED FROM, BASED ON, AND/OR DERIVED FROM THIS SOURCE
 * CODE FILE.
 * ===============================================================================================
 */
package com.npower.dm.hibernate.management;

import java.io.InputStream;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;

import org.apache.commons.digester.Digester;
import org.hibernate.Session;

import com.npower.dm.core.DDFNode;
import com.npower.dm.core.DDFNodeAccessType;
import com.npower.dm.core.DDFNodeMIMEType;
import com.npower.dm.core.DDFTree;
import com.npower.dm.core.DMException;
import com.npower.dm.core.Model;
import com.npower.dm.hibernate.entity.DDFNodeEntity;
import com.npower.dm.hibernate.entity.DDFTreeEntity;
import com.npower.dm.hibernate.entity.ModelDDFTree;
import com.npower.dm.hibernate.entity.ModelEntity;
import com.npower.dm.management.DDFTreeBean;
import com.npower.dm.management.ManagementBeanFactory;
import com.npower.dm.util.DDFTreeHelper;
import com.npower.dm.xml.LocalEntityResolver;

/**
 * 
 * @author Zhao DongLu
 * @version $Revision: 1.15 $ $Date: 2008/12/15 03:24:14 $
 */
public class DDFTreeManagementBeanImpl extends AbstractBean implements DDFTreeBean {

  /**
   * Private Default Constructor
   */
  protected DDFTreeManagementBeanImpl() {
    super();
  }

  DDFTreeManagementBeanImpl(ManagementBeanFactory factory, Session session) {
    super(factory, session);
  }

  // Method's related with DDFTreeEntity
  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.hb.DDFTreeBean#getDDFTreeByID(long)
   */
  public DDFTree getDDFTreeByID(long id) throws DMException {
    Session session = null;
    try {
      session = this.getHibernateSession();
      DDFTree tree = (DDFTree) session.get(DDFTreeEntity.class, new Long(id));
      return tree;
    } catch (Exception e) {
      throw new DMException(e);
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.DDFTreeBean#getDDFTreeByID(java.lang.String)
   */
  public DDFTree getDDFTreeByID(String id) throws DMException {
    return this.getDDFTreeByID(Long.parseLong(id));
  }

  /**
   * Add the DDFNodeEntity into DMInventory and the children of the node. The
   * tree is context, not indicate the node is root of tree.
   * 
   * @param tree
   * @param node
   * @throws DMException
   */
  private void addDDFNode(DDFTree tree, DDFNode node) throws DMException {
    Session session = null;
    try {
      session = this.getHibernateSession();
      // Add the node first
      // Caution: current node must be add at first!!!
      session.save(node);

      Set<DDFNodeMIMEType> types = node.getDdfNodeMIMETypes();
      for (Iterator<DDFNodeMIMEType> i = types.iterator(); i.hasNext();) {
        DDFNodeMIMEType type = i.next();
        // Link references between MIMETypeID and DDFNodeEntity
        type.getID().setDdfNodeId(node.getID());
        // Link references between DDFNodeMIMETypeEntity and DDFNodeEntity
        type.setDdfNode(node);

        session.save(type);
      }
      Set<DDFNodeAccessType> accessTypes = node.getDdfNodeAccessTypes();
      for (Iterator<DDFNodeAccessType> i = accessTypes.iterator(); i.hasNext();) {
        DDFNodeAccessType accessType = i.next();
        // Link references between AccessTypeID and DDFNodeEntity
        accessType.getID().setDdfNodeId(node.getID());
        // Link references between AccessType and DDFNodeEntity
        accessType.setDdfNode(node);

        session.save(accessType);
      }

      // Add children
      Set<DDFNode> children = node.getChildren();
      for (Iterator<DDFNode> i = children.iterator(); i.hasNext();) {
        DDFNode child = i.next();
        child.setDdfTree(tree);
        child.setParentDDFNode(node);

        this.addDDFNode(tree, child);
      }

    } catch (Exception e) {
      throw new DMException(e);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.hb.DDFTreeBean#addDDFTree(com.npower.dm.hibernate.DDFTreeEntity)
   */
  public void addDDFTree(DDFTree tree) throws DMException {
    Session session = null;
    try {
      session = this.getHibernateSession();
      session.save(tree);

      Set<DDFTreeEntity> nodes = ((DDFTreeEntity)tree).getDdfNodes();
      for (Iterator<DDFTreeEntity> i = nodes.iterator(); i.hasNext();) {
          DDFNode node = (DDFNode) i.next();
          node.setDdfTree(tree);
          this.addDDFNode(tree, node);
      }
    } catch (Exception e) {
      throw new DMException(e);
    }
  }

  /**
   * Create digester for DDF XML. This digester rule is based on OMA DDF Spec
   * 1.1 Before call this method, push a DDFTreeEntity object into digester
   * stack.
   * 
   * @return Digester
   */
  private Digester createDDFTreeDigester() {

    // Initialize the digester
    Digester digester = new Digester();
    digester.setEntityResolver(new LocalEntityResolver());
    digester.setValidating(false);

    // Parsing DDF
    // digester.addObjectCreate("MgmtTree",
    // "com.npower.dm.hibernate.DDFTreeEntity");
    digester.addBeanPropertySetter("MgmtTree/Man", "manufacturer");
    digester.addBeanPropertySetter("MgmtTree/Mod", "model");
    digester.addBeanPropertySetter("MgmtTree/VerDTD", "specVersion");

    digester.addObjectCreate("*/Node", "com.npower.dm.hibernate.entity.DDFNodeEntity");
    digester.addBeanPropertySetter("*/Node/NodeName", "name");
    digester.addBeanPropertySetter("*/Node/Path", "path");
    digester.addBeanPropertySetter("*/Node/DFProperties/Description", "description");
    digester.addBeanPropertySetter("*/Node/DFProperties/DFTitle", "title");
    digester.addBeanPropertySetter("*/Node/DFProperties/DefaultValue", "defaultValue");

    digester.addBeanPropertySetter("*/Node/DFProperties/DFFormat/b64", "formatB64");
    digester.addBeanPropertySetter("*/Node/DFProperties/DFFormat/bin", "formatBin");
    digester.addBeanPropertySetter("*/Node/DFProperties/DFFormat/bool", "formatBool");
    digester.addBeanPropertySetter("*/Node/DFProperties/DFFormat/chr", "formatChr");
    digester.addBeanPropertySetter("*/Node/DFProperties/DFFormat/int", "formatInt");
    digester.addBeanPropertySetter("*/Node/DFProperties/DFFormat/node", "formatNode");
    digester.addBeanPropertySetter("*/Node/DFProperties/DFFormat/xml", "formatXml");
    digester.addBeanPropertySetter("*/Node/DFProperties/DFFormat/null", "formatNull");

    digester.addBeanPropertySetter("*/Node/DFProperties/Occurrence/One", "occurrenceOne");
    digester.addBeanPropertySetter("*/Node/DFProperties/Occurrence/ZeroOrOne", "occurrenceZeroOrOne");
    digester.addBeanPropertySetter("*/Node/DFProperties/Occurrence/ZeroOrMore", "occurrenceZeroOrMore");
    digester.addBeanPropertySetter("*/Node/DFProperties/Occurrence/OneOrMore", "occurrenceOneOrMore");
    digester.addBeanPropertySetter("*/Node/DFProperties/Occurrence/ZeroOrN", "occurrenceZeroOrN");
    digester.addBeanPropertySetter("*/Node/DFProperties/Occurrence/OneOrN", "occurrenceOneOrN");

    digester.addBeanPropertySetter("*/Node/DFProperties/Scope/Permanent", "scopePermanent");
    digester.addBeanPropertySetter("*/Node/DFProperties/Scope/Dynamic", "scopeDynamic");

    digester.addBeanPropertySetter("*/Node/DFProperties/AccessType/Add", "accessTypeAdd");

    digester.addBeanPropertySetter("*/Node/DFProperties/AccessType/Copy", "accessTypeCopy");
    digester.addBeanPropertySetter("*/Node/DFProperties/AccessType/Delete", "accessTypeDelete");
    digester.addBeanPropertySetter("*/Node/DFProperties/AccessType/Exec", "accessTypeExec");
    digester.addBeanPropertySetter("*/Node/DFProperties/AccessType/Get", "accessTypeGet");
    digester.addBeanPropertySetter("*/Node/DFProperties/AccessType/Replace", "accessTypeReplace");

    // digester.addBeanPropertySetter("*/Node/DFProperties/DFType/DDFName",
    // "ddfName");
    digester.addBeanPropertySetter("*/Node/DFProperties/DFType/MIME", "mimeTypeString");

    digester.addSetNext("*/Node", "add", "com.npower.dm.hibernate.entity.DDFNodeEntity");

    // digester.addSetNext("MgmtTree", "add", "DDFTreeEntity");

    return (digester);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.hb.DDFTreeBean#parseDDFTree(java.io.InputStream)
   */
  public DDFTree parseDDFTree(InputStream in) throws DMException {
    // Create and execute our Digester
    Digester digester = createDDFTreeDigester();
    try {
      // Push a DDFTreeEntity into the stack.
      DDFTreeEntity tree = new DDFTreeEntity();
      digester.push(tree);
      digester.parse(in);
      
      // Ignore the "." node in DDF file. testcase: SonyErricsson W900i_2.0.xml
      /*
      Set<DDFNode> rootNodes = tree.getRootDDFNodes();
      if (rootNodes.size() == 1) {
         DDFNode rootNode = rootNodes.iterator().next();
         String name = rootNode.getName();
         if (name != null && name.equals(".")) {
            Set<DDFNode> children = rootNode.getChildren();
            
            tree.getRootDDFNodes().clear();
            tree.getRootDDFNodes().addAll(children);
            for (DDFNode child: children) {
                child.setParentDDFNode(null);
            }
            
            tree.getDdfNodes().remove(rootNode);
            tree.getDdfNodes().addAll(children);
         }
      }
      */
      Set<DDFNode> rootNodes = tree.getRootDDFNodes();
      boolean includeRootNode = false;
      for (DDFNode node: rootNodes) {
          String name = node.getName();
          if (name != null && name.equals(".")) {
             includeRootNode = true;
             break;
          }
      }
      if (!includeRootNode) {
         DDFNodeEntity rootDDFNode = new DDFNodeEntity();
         rootDDFNode.setDdfTree(tree);
         rootDDFNode.setName(".");
         rootDDFNode.setParentDDFNode(null);
         rootDDFNode.getChildren().addAll(rootNodes);
         
         tree.add(rootDDFNode);
         tree.getRootDDFNodes().clear();
         tree.getRootDDFNodes().add(rootDDFNode);
         
         for (DDFNode child: rootNodes) {
             child.setParentDDFNode(rootDDFNode);
             tree.getDdfNodes().removeAll(rootNodes);
         }
      }
      return tree;
    } catch (Exception e) {
      throw new DMException(e);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.hb.DDFTreeBean#delete(com.npower.dm.hibernate.DDFNodeEntity)
   */
  public void delete(DDFNode node) throws DMException {
    Session session = this.getHibernateSession();
    try {
      // Delete MIMTypes related with Node
      Set<DDFNodeMIMEType> mTypes = node.getDdfNodeMIMETypes();
      for (Iterator<DDFNodeMIMEType> i = mTypes.iterator(); i.hasNext();) {
        session.delete(i.next());
      }

      // Delete AccessTypes related with the node
      Set<DDFNodeAccessType> aTypes = node.getDdfNodeAccessTypes();
      for (Iterator<DDFNodeAccessType> i = aTypes.iterator(); i.hasNext();) {
        session.delete(i.next());
      }

      // Delete children
      Set<DDFNode> children = node.getChildren();
      for (Iterator<DDFNode> i = children.iterator(); i.hasNext();) {
        DDFNode child = (DDFNode) i.next();
        this.delete(child);
      }

      // Delete the node
      session.delete(node);

    } catch (Exception e) {
      throw new DMException(e);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.hb.DDFTreeBean#delete(com.npower.dm.hibernate.DDFTreeEntity)
   */
  public void delete(DDFTree tree) throws DMException {
    Session session = this.getHibernateSession();
    try {
        // Delete ddf nodes
        Set<DDFNode> nodes = ((DDFTreeEntity)tree).getDdfNodes();
        for (Iterator<DDFNode> i = nodes.iterator(); i.hasNext();) {
          DDFNode node = (DDFNode) i.next();
          this.delete(node);
        }
  
        session.delete(tree);
    } catch (Exception e) {
      throw new DMException(e);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.hb.DDFTreeBean#findDDFNodeByNodePath(com.npower.dm.hibernate.ModelEntity,
   *      java.lang.String)
   */
  public DDFNode findDDFNodeByNodePath(Model model, String nodePath) throws DMException {
    Set<ModelDDFTree> set = ((ModelEntity)model).getModelDDFTrees();
    for (Iterator<ModelDDFTree> i = set.iterator(); i.hasNext();) {
        ModelDDFTree mTree = (ModelDDFTree) i.next();
        DDFTree tree = mTree.getDdfTree();
        DDFNode node = this.findDDFNodeByNodePath(tree, nodePath);
        if (node != null) {
          return node;
        }
    }
    return null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.hb.DDFTreeBean#findDDFNodeByNodePath(com.npower.dm.hibernate.DDFTreeEntity,
   *      java.lang.String)
   */
  public DDFNode findDDFNodeByNodePath(DDFTree tree, String nodePath) throws DMException {
    if (nodePath == null) {
      throw new DMException("The node path is NULL");
    }
    if (nodePath.equalsIgnoreCase("./")) {
      throw new DMException("Node path is '" + nodePath + "', could not find the root node.");
    }
    if (tree == null) {
      throw new DMException("The DDFTreeEntity is NULL");
    }

    // Re-caculate root path, remove prefix "./" and "/", remove end of "/".
    nodePath = nodePath.trim();
    if (nodePath.startsWith("./")) {
       //nodePath = nodePath.substring(2, nodePath.length());
    } else if (nodePath.startsWith("/")) {
      nodePath = "." + nodePath;
       //nodePath = nodePath.substring(1, nodePath.length());
    } else {
      nodePath = "./" + nodePath;
    }
    if (nodePath.endsWith("/")) {
      nodePath = nodePath.substring(0, nodePath.length() - 1);
    }
    Set<DDFNode> nodeSet = ((DDFTreeEntity)tree).getDdfNodes();
    // Recursion
    return this.findDDFNode(nodeSet, nodePath);
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.npower.dm.management.hb.DDFTreeBean#findDDFNode(java.util.Set,
   *      java.lang.String)
   */
  public DDFNode findDDFNode(Set<DDFNode> rootNodesSet, String nodePath) {
    for (Iterator<DDFNode> nodes = rootNodesSet.iterator(); nodes.hasNext();) {
        DDFNode node = (DDFNode) nodes.next();
        if (nodePath.equals(node.getName())) {
          return node;
        }
        Matcher matcher = DDFTreeHelper.DynamicNamePattern.matcher(nodePath);
        boolean equals = matcher.matches();
        matcher.reset();
        boolean found = matcher.find();
        boolean startWith = false;
        if (found) {
           int index = matcher.start();
           startWith = (index == 0)?true:false;
        }

        if (equals && (node.getName() == null || node.getName().trim().length() == 0)) {
           return node;
        } else if (node.getName() == null || node.getName().trim().length() == 0) {
          if (startWith) {
            return findDDFNode(node.getChildren(), nodePath.substring(nodePath.indexOf("/") + 1, nodePath.length()));
          } else {
            continue;
          }
        } else if (nodePath.startsWith(node.getName() + "/")) {
          return findDDFNode(node.getChildren(), nodePath.substring(nodePath.indexOf("/") + 1, nodePath.length()));
        }
    }
    return null;
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.DDFTreeBean#findDDFNodeByRelativePath(com.npower.dm.core.Model, com.npower.dm.core.DDFNode, java.lang.String)
   */
  public DDFNode findDDFNodeByRelativePath(Model model, DDFNode baseNode, String nodeRelativePath) throws DMException {
    String basePath = baseNode.getNodePath();
    String nodePath = DDFTreeHelper.concat(basePath, nodeRelativePath);
    
    return this.findDDFNodeByNodePath(model, nodePath);
  }
  
  /* (non-Javadoc)
   * @see com.npower.dm.management.DDFTreeBean#getDDFNodeByID(long)
   */
  public DDFNode getDDFNodeByID(long id) throws DMException {
    Session session = null;
    try {
        session = this.getHibernateSession();
        DDFNode node = (DDFNode) session.get(DDFNodeEntity.class, new Long(id));
        return node;
    } catch (Exception e) {
      throw new DMException(e);
    }
  }

  /* (non-Javadoc)
   * @see com.npower.dm.management.DDFTreeBean#getDDFNodeByID(java.lang.String)
   */
  public DDFNode getDDFNodeByID(String id) throws DMException {
    return this.getDDFNodeByID(Long.parseLong(id));
  }

}
