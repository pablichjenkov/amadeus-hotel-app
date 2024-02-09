import SwiftUI
import HotelBookingKt

@main
struct iOSDemoAppApp: App {

    var body: some Scene {
       WindowGroup {
           ZStack {
               Color.white.ignoresSafeArea(.all) // status bar color
               ContentView()
                   .onReceive(NotificationCenter.default.publisher(for: UIApplication.willEnterForegroundNotification)) { _ in
                    
                       print("application_willEnterForeground")
                       
                   }
                   .onReceive(NotificationCenter.default.publisher(for: UIApplication.didBecomeActiveNotification)) { _ in
                       
                       print("application_didBecomeActive")
                       
                   }
                   .onReceive(NotificationCenter.default.publisher(for: UIApplication.willResignActiveNotification)) { _ in
                       
                       print("application_willResignActive")
                       
                   }.onReceive(NotificationCenter.default.publisher(for: UIApplication.didEnterBackgroundNotification)) { _ in
                       
                       print("application_didEnterBackground")
                   }
           }.preferredColorScheme(.light)

       }
        
   }

}
