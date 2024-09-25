package zzz.com.micodeya.backend.core.service.fcm.dto;

import java.util.List;

import lombok.Data;

@Data
public class SubscriptionTopicFcmDto {
	    
	    private String fcmTopic;
	    private List<String> fcmTokenList;
	   
}
