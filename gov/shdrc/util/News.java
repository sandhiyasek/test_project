package gov.shdrc.util;

public class News {
		private Long messageId=null;
		private String messageHeader=null;
		private String message=null;
		private String addedBy=null;
		private String addedOn=null;
		public Long getMessageId() {
			return messageId;
		}
		public void setMessageId(Long messageId) {
			this.messageId = messageId;
		}
		public String getMessageHeader() {
			return messageHeader;
		}
		public void setMessageHeader(String messageHeader) {
			this.messageHeader = messageHeader;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public String getAddedBy() {
			return addedBy;
		}
		public void setAddedBy(String addedBy) {
			this.addedBy = addedBy;
		}
		public String getAddedOn() {
			return addedOn;
		}
		public void setAddedOn(String addedOn) {
			this.addedOn = addedOn;
		}
		
}
