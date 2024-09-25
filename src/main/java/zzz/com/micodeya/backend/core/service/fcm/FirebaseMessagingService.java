package zzz.com.micodeya.backend.core.service.fcm;

import java.util.List;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import com.google.firebase.messaging.BatchResponse;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;
import com.google.firebase.messaging.TopicManagementResponse;
import com.google.firebase.messaging.TopicManagementResponse.Error;

import zzz.com.micodeya.backend.core.service.fcm.dto.SendMultipleFcmDto;
import zzz.com.micodeya.backend.core.service.fcm.dto.SendOneFcmDto;
import zzz.com.micodeya.backend.core.service.fcm.dto.SendToTopicFcmDto;
import zzz.com.micodeya.backend.core.service.fcm.dto.SubscriptionTopicFcmDto;

@Service
public class FirebaseMessagingService {

	private final FirebaseMessaging firebaseMessaging;

	public FirebaseMessagingService(@Nullable FirebaseMessaging firebaseMessaging) {
		this.firebaseMessaging = firebaseMessaging;
	}

	/*
	 * Enviamos un push a un dispositivo especifico por medio de token
	 */
	public String sendOneNotification(SendOneFcmDto pushMsg) throws FirebaseMessagingException {

		Notification notification = Notification
				.builder()
				.setTitle(pushMsg.getTitulo())
				.setBody(pushMsg.getContenido())
				.setImage(pushMsg.getUrlImagen())
				.build();

		Message message = Message
				.builder()
				.setToken(pushMsg.getFcmToken())
				.setNotification(notification)
				.putAllData(pushMsg.getCargaUtil())
				.build();

		String response = firebaseMessaging.send(message);

		return response;
	}

	public String sendMultiple(SendMultipleFcmDto pushMsg) throws FirebaseMessagingException {

		Notification notification = Notification
				.builder()
				.setTitle(pushMsg.getTitulo())
				.setBody(pushMsg.getContenido())
				.setImage(pushMsg.getUrlImagen())
				.build();

		MulticastMessage message = MulticastMessage.builder()
				.setNotification(notification)
				.putAllData(pushMsg.getCargaUtil())
				.addAllTokens(pushMsg.getFcmTokenList())
				.build();

		BatchResponse response = FirebaseMessaging.getInstance().sendMulticast(message);
		// See the BatchResponse reference documentation
		// for the contents of response.
		/*
		 * for (SendResponse sendResponse : response.getResponses()) {
		 * 
		 * }
		 */
		String resultado = String.format("correcto: %s, incorrecto: %s ", response.getSuccessCount(),
				response.getFailureCount());

		System.out.println("sendMultiple: " + resultado);

		return resultado;
	}

	/*
	 * enviamos un push a varios dispositivos por medio de topics
	 */
	public List<String> postToClients(SubscriptionTopicFcmDto message) throws FirebaseMessagingException {
		/*
		 * MulticastMessage msg = MulticastMessage.builder()
		 * .addAllTokens(message.getRegistrationTokens())
		 * .putData("Hola mundo", message.getData())
		 * .build();
		 * 
		 * BatchResponse response = firebaseMessaging.sendMulticast(msg);
		 * 
		 * List<String> ids = response.getResponses()
		 * .stream()
		 * .map(r -> r.getMessageId())
		 * .collect(Collectors.toList());
		 * 
		 * return ids;
		 */
		return null;

	}

	/*
	 * Subscribirse a topics
	 * https://firebase.google.com/docs/cloud-messaging/manage-topics?hl=es-419
	 */
	public String subscribeToTopic(SubscriptionTopicFcmDto subscriptionTopic)
			throws FirebaseMessagingException {
		TopicManagementResponse response = firebaseMessaging.subscribeToTopic(subscriptionTopic.getFcmTokenList(),
				subscriptionTopic.getFcmTopic());

		for (Error err : response.getErrors()) {
			System.out.println("[ERROR SUBSCRIBE]" + err.toString());
		}

		String resultado = String.format("correcto: %s, incorrecto: %s ", response.getSuccessCount(),
				response.getFailureCount());

		System.out.println("subscribeToTopic: " + resultado);

		return resultado;
	}

	public String unsubscribeFromTopic(SubscriptionTopicFcmDto subscriptionTopic)
			throws FirebaseMessagingException {
		TopicManagementResponse response = firebaseMessaging.unsubscribeFromTopic(subscriptionTopic.getFcmTokenList(),
				subscriptionTopic.getFcmTopic());

		for (Error err : response.getErrors()) {
			System.out.println("[ERROR UNSUBSCRIBE]" + err.toString());
		}

		String resultado = String.format("correcto: %s, incorrecto: %s ", response.getSuccessCount(),
				response.getFailureCount());

		System.out.println("unsubscribeFromTopic: " + resultado);

		return resultado;
	}

	/*
	 * Envio de push a un topic
	 */
	public String sendToTopic(SendToTopicFcmDto topicMsg) throws FirebaseMessagingException {
		Notification notification = Notification
				.builder()
				.setTitle(topicMsg.getTitulo())
				.setBody(topicMsg.getContenido())
				.setImage(topicMsg.getUrlImagen())
				.build();

		Message msg = Message.builder()
				.setTopic(topicMsg.getFcmTopic())
				.setNotification(notification)
				.putAllData(topicMsg.getCargaUtil())
				.build();

		String id = firebaseMessaging.send(msg);

		return id;

	}

}