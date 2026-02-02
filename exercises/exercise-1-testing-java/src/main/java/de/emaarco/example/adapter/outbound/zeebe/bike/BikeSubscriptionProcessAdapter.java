package de.emaarco.example.adapter.outbound.zeebe.bike;

import de.emaarco.example.adapter.process.config.ProcessEngineApi;
import de.emaarco.example.adapter.process.generated.BikeSubscriptionSignupProcessApi;
import de.emaarco.example.application.port.outbound.bike.BikeSubscriptionProcess;
import de.emaarco.example.domain.bike.BikeSubscriptionId;
import org.springframework.stereotype.Component;

import java.util.Map;

import static de.emaarco.example.adapter.process.generated.BikeSubscriptionSignupProcessApi.PROCESS_ID;
import static de.emaarco.example.adapter.process.generated.BikeSubscriptionSignupProcessApi.Variables.SUBSCRIPTION_ID;

@Component
public class BikeSubscriptionProcessAdapter implements BikeSubscriptionProcess {
	
	private final ProcessEngineApi engineApi;
	
	public BikeSubscriptionProcessAdapter(ProcessEngineApi engineApi) {
		this.engineApi = engineApi;
	}
	
	@Override
	public long startSubscription(BikeSubscriptionId id) {
		Map<String, Object> variables = Map.of(SUBSCRIPTION_ID, id.value().toString());
		return engineApi.startProcess(PROCESS_ID, variables);
	}
	
	@Override
	public void sendPaymentReceived(BikeSubscriptionId id) {
		engineApi.sendMessage(
				BikeSubscriptionSignupProcessApi.Messages.MESSAGE_PAYMENT_RECEIVED,
				id.value().toString()
		);
	}
	
	@Override
	public void sendRequestCanceled(BikeSubscriptionId id) {
		engineApi.sendMessage(
				BikeSubscriptionSignupProcessApi.Messages.MESSAGE_REQUEST_CANCELED,
				id.value().toString()
		);
	}
	
	@Override
	public void sendBikeReceived(BikeSubscriptionId id) {
		engineApi.sendMessage(
				BikeSubscriptionSignupProcessApi.Messages.MESSAGE_BIKE_RECEIVED,
				id.value().toString()
		);
	}
	
}
