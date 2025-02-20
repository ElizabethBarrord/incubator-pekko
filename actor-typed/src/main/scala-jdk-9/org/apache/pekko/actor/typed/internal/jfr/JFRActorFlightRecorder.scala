/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * license agreements; and to You under the Apache License, version 2.0:
 *
 *   https://www.apache.org/licenses/LICENSE-2.0
 *
 * This file is part of the Apache Pekko project, which was derived from Akka.
 */

/*
 * Copyright (C) 2009-2022 Lightbend Inc. <https://www.lightbend.com>
 */

package org.apache.pekko.actor.typed.internal.jfr

import org.apache.pekko
import pekko.actor.ActorPath
import pekko.actor.typed.ActorSystem
import pekko.actor.typed.internal.ActorFlightRecorder
import pekko.actor.typed.internal.DeliveryFlightRecorder
import pekko.annotation.InternalApi

/**
 * INTERNAL API
 */
@InternalApi
private[pekko] final class JFRActorFlightRecorder() extends ActorFlightRecorder {
  override val delivery: DeliveryFlightRecorder = new JFRDeliveryFlightRecorder
}

/**
 * INTERNAL API
 */
@InternalApi private[pekko] final class JFRDeliveryFlightRecorder extends DeliveryFlightRecorder {

  override def producerCreated(producerId: String, path: ActorPath): Unit =
    new DeliveryProducerCreated(producerId, path.toString).commit()
  override def producerStarted(producerId: String, path: ActorPath): Unit =
    new DeliveryProducerStarted(producerId, path.toString).commit()
  override def producerRequestNext(producerId: String, currentSeqNr: Long, confirmedSeqNr: Long): Unit =
    new DeliveryProducerRequestNext(producerId, currentSeqNr, confirmedSeqNr).commit()
  override def producerSent(producerId: String, seqNr: Long): Unit =
    new DeliveryProducerSent(producerId, seqNr).commit()
  override def producerWaitingForRequest(producerId: String, currentSeqNr: Long): Unit =
    new DeliveryProducerWaitingForRequest(producerId, currentSeqNr).commit()
  override def producerResentUnconfirmed(producerId: String, fromSeqNr: Long, toSeqNr: Long): Unit =
    new DeliveryProducerResentUnconfirmed(producerId, fromSeqNr, toSeqNr).commit()
  override def producerResentFirst(producerId: String, firstSeqNr: Long): Unit =
    new DeliveryProducerResentFirst(producerId, firstSeqNr).commit()
  override def producerResentFirstUnconfirmed(producerId: String, seqNr: Long): Unit =
    new DeliveryProducerResentFirstUnconfirmed(producerId, seqNr).commit()
  override def producerReceived(producerId: String, currentSeqNr: Long): Unit =
    new DeliveryProducerReceived(producerId, currentSeqNr).commit()
  override def producerReceivedRequest(producerId: String, requestedSeqNr: Long, confirmedSeqNr: Long): Unit =
    new DeliveryProducerReceivedRequest(producerId, requestedSeqNr, confirmedSeqNr).commit()
  override def producerReceivedResend(producerId: String, fromSeqNr: Long): Unit =
    new DeliveryProducerReceivedResend(producerId, fromSeqNr).commit()

  override def consumerCreated(path: ActorPath): Unit =
    new DeliveryConsumerCreated(path.toString).commit()
  override def consumerStarted(path: ActorPath): Unit =
    new DeliveryConsumerStarted(path.toString).commit()
  override def consumerReceived(producerId: String, seqNr: Long): Unit =
    new DeliveryConsumerReceived(producerId, seqNr).commit()
  override def consumerReceivedPreviousInProgress(producerId: String, seqNr: Long, stashed: Int): Unit =
    new DeliveryConsumerReceivedPreviousInProgress(producerId, seqNr: Long, stashed).commit()
  override def consumerDuplicate(producerId: String, expectedSeqNr: Long, seqNr: Long): Unit =
    new DeliveryConsumerDuplicate(producerId, expectedSeqNr, seqNr).commit()
  override def consumerMissing(producerId: String, expectedSeqNr: Long, seqNr: Long): Unit =
    new DeliveryConsumerMissing(producerId, expectedSeqNr, seqNr).commit()
  override def consumerReceivedResend(seqNr: Long): Unit =
    new DeliveryConsumerReceivedResend(seqNr).commit()
  override def consumerSentRequest(producerId: String, requestedSeqNr: Long): Unit =
    new DeliveryConsumerSentRequest(producerId, requestedSeqNr).commit()
  override def consumerChangedProducer(producerId: String): Unit =
    new DeliveryConsumerChangedProducer(producerId).commit()
  override def consumerStashFull(producerId: String, seqNr: Long): Unit =
    new DeliveryConsumerStashFull(producerId, seqNr).commit()

}
