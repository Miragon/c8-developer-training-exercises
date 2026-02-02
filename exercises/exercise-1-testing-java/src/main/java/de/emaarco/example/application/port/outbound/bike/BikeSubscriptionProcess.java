package de.emaarco.example.application.port.outbound.bike;

import de.emaarco.example.domain.bike.BikeSubscriptionId;

public interface BikeSubscriptionProcess {
	long startSubscription(BikeSubscriptionId id);
	
	void sendPaymentReceived(BikeSubscriptionId id);
	
	void sendRequestCanceled(BikeSubscriptionId id);
	
	void sendBikeReceived(BikeSubscriptionId id);
}
