package homepagemodule;

public class FoundItem {
        private String itemName; // Name of the found item
        private String brandName;
        private String modelName;
        private String imeiNumber;
        private String placeOfLost;
        private String watchType;
        private String dateOfLost;
        private String color;
        private String uniqueness;
        private String typeOfBag;

        // Default constructor (required for Firebase serialization)
        public FoundItem() {
        }

        // Parameterized constructor
        public FoundItem(String itemName, String brandName, String modelName, String imeiNumber, String placeOfLost,
                         String watchType, String dateOfLost, String color, String uniqueness, String typeOfBag) {
            this.itemName = itemName;
            this.brandName = brandName;
            this.modelName = modelName;
            this.imeiNumber = imeiNumber;
            this.placeOfLost = placeOfLost;
            this.watchType = watchType;
            this.dateOfLost = dateOfLost;
            this.color = color;
            this.uniqueness = uniqueness;
            this.typeOfBag = typeOfBag;
        }

        // Getter and setter methods
        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public String getBrandName() {
            return brandName;
        }

        public void setBrandName(String brandName) {
            this.brandName = brandName;
        }

        public String getModelName() {
            return modelName;
        }

        public void setModelName(String modelName) {
            this.modelName = modelName;
        }

        public String getImeiNumber() {
            return imeiNumber;
        }

        public void setImeiNumber(String imeiNumber) {
            this.imeiNumber = imeiNumber;
        }

        public String getPlaceOfLost() {
            return placeOfLost;
        }

        public void setPlaceOfLost(String placeOfLost) {
            this.placeOfLost = placeOfLost;
        }

        public String getWatchType() {
            return watchType;
        }

        public void setWatchType(String watchType) {
            this.watchType = watchType;
        }

        public String getDateOfLost() {
            return dateOfLost;
        }

        public void setDateOfLost(String dateOfLost) {
            this.dateOfLost = dateOfLost;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String getUniqueness() {
            return uniqueness;
        }

        public void setUniqueness(String uniqueness) {
            this.uniqueness = uniqueness;
        }

        public String getTypeOfBag() {
            return typeOfBag;
        }

        public void setTypeOfBag(String typeOfBag) {
            this.typeOfBag = typeOfBag;
        }
    }