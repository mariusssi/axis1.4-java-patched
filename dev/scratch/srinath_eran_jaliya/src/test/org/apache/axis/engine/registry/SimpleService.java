/*
 * Copyright 2001-2004 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.axis.engine.registry;

import java.util.HashMap;

import javax.xml.namespace.QName;

import org.apache.axis.AxisFault;
import org.apache.axis.engine.MessageContext;
import org.apache.axis.engine.Operation;
import org.apache.axis.engine.Service;
import org.apache.axis.registry.CommonExecuterStateFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SimpleService extends AbstractCommonExecuter implements Service{
    private Log log = LogFactory.getLog(getClass());     
    private HashMap operations = new HashMap();
    private QName name;
    
    public SimpleService(QName name){
        super(CommonExecuterStateFactory.getServiceState());
        this.name = name;
    } 
    public ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    public Operation getOperation(QName index) {
        return (Operation)operations.get(index);
    }

    public int getOperationCount() {
        return operations.size();
    }

    public void recive(MessageContext mc) throws AxisFault {
        super.recive(mc);  
        QName methodName = mc.getCurrentOperation();
        Operation operation = getOperation(methodName);
        log.info("find operation "+methodName); 
        operation.recive(mc);
    }

    public void send(MessageContext mc) throws AxisFault {
        super.send(mc);        
        QName methodName = mc.getCurrentOperation();
        Operation operation = getOperation(methodName);
        log.info("find operation "+methodName); 
        operation.send(mc);
    }

    public QName getName() {
        return name;
    }

    public void addOperation(Operation op) {
        operations.put(op.getName(),op);
    }

}
