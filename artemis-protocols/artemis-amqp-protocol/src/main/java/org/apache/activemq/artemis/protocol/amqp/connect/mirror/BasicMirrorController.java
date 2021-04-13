/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.activemq.artemis.protocol.amqp.connect.mirror;

import org.apache.activemq.artemis.core.server.ActiveMQServer;
import org.apache.qpid.proton.engine.Link;

public class BasicMirrorController<T extends Link> {

   protected final ActiveMQServer server;

   protected T link;

   protected short remoteMirrorId = -1;

   protected final short localMirrorId;

   protected final byte mirrorIDForRouting;

   public short getLocalMirrorId() {
      return this.localMirrorId;
   }

   public short getRemoteMirrorId() {
      if (remoteMirrorId < 0 && link != null && link.getRemoteProperties() != null && link.getRemoteProperties().containsKey(AMQPMirrorControllerSource.BROKER_ID)) {
         remoteMirrorId = ((Number)link.getRemoteProperties().get(AMQPMirrorControllerSource.BROKER_ID)).shortValue();
      }
      return remoteMirrorId;
   }

   public BasicMirrorController(ActiveMQServer server) {
      this.server = server;
      this.localMirrorId = server.getMirrorBrokerId();
      this.mirrorIDForRouting = (byte)localMirrorId;
   }

   public T getLink() {
      return link;
   }

   public BasicMirrorController<T> setLink(T link) {
      this.link = link;
      return this;
   }
}
