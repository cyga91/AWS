//package helloworld.constant;
//
//import lombok.Getter;
//import lombok.Setter;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.Optional;
//
//@Getter
//@Setter
//@Configuration
//public class PropertiesProvider {
//////    @Value("${retryLogic.max-redeliveries}")
////    public static String maxRedeliveries(){
////        return getProperty("max-redeliveries");
////    };
////    @Value("${retryLogic.redelivery-delay}")
////    public static long redeliveryDelay;
////
////    private static String getProperty(String propertyName) {
////        return Optional.ofNullable(System.getProperty(propertyName)).orElseGet(() -> System.getenv(propertyName));
////    }
////
////    public static void main(String[] args) {
//////        PropertiesProvider propertiesProvider = new PropertiesProvider();
//////        System.out.println(propertiesProvider.getMaxRedeliveries());
//////        System.out.println(propertiesProvider.getRedeliveryDelay());
////        System.out.println(maxRedeliveries());
////        System.out.println(redeliveryDelay);
////    }
//    public static final int maxRedeliveries = getMaxRedeliveries();
//
//    private static int getMaxRedeliveries() {
//
//    }
//}
