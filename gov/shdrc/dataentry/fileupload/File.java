package gov.shdrc.dataentry.fileupload;

public class File {
		private Long fileId=null;
		private Integer directorateId=null;
		private Integer fileType=null;
		private String fileName=null;
		private String frequency=null;
		private String fileExtension=null;
		private String addedBy=null;
		private String addedOn=null;
		
		public Long getFileId() {
			return fileId;
		}
		public void setFileId(Long fileId) {
			this.fileId = fileId;
		}
		public Integer getDirectorateId() {
			return directorateId;
		}
		public void setDirectorateId(Integer directorateId) {
			this.directorateId = directorateId;
		}
		public Integer getFileType() {
			return fileType;
		}
		public void setFileType(Integer fileType) {
			this.fileType = fileType;
		}
		public String getFileName() {
			return fileName;
		}
		public void setFileName(String fileName) {
			this.fileName = fileName;
		}
		public String getFrequency() {
			return frequency;
		}
		public void setFrequency(String frequency) {
			this.frequency = frequency;
		}
		public String getFileExtension() {
			return fileExtension;
		}
		public void setFileExtension(String fileExtension) {
			this.fileExtension = fileExtension;
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
