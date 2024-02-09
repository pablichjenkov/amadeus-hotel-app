import SwiftUI
import HotelBookingKt

struct ComposeView : UIViewControllerRepresentable {

    func makeUIViewController(context: Context) -> UIViewController {
        
        let iosAppRootComponent = BindingsKt.buildAppComponent()
        
        let mainViewController = BindingsKt.ComponentRenderer(
            rootComponent: iosAppRootComponent,
            onBackPress: {
                exit(0)
            }
        )

        return mainViewController
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {

    var body: some View {
        ComposeView()
                .ignoresSafeArea(.keyboard) // Compose has own keyboard handler
                //.ignoresSafeArea(.all, edges: .bottom) // If prefered to handle this in compose land

    }
}

