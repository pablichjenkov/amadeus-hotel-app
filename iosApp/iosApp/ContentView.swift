import SwiftUI
import HotelBookingKt

struct ComposeView : UIViewControllerRepresentable {

    var iosBridge: IosBridge

    func makeUIViewController(context: Context) -> UIViewController {
        let iosAppRootComponent = BindingsKt.buildAppComponent()
        let mainViewController = BindingsKt.ComponentRenderer(
            rootComponent: iosAppRootComponent,
            iosBridge: iosBridge,
            onBackPress: {
                exit(0)
            }
        )

        return mainViewController
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {

    var iosBridge: IosBridge

    var body: some View {
        ComposeView(iosBridge: iosBridge)
                .ignoresSafeArea(.keyboard) // Compose has own keyboard handler
                //.ignoresSafeArea(.all, edges: .bottom) // If prefered to handle this in compose land

    }
}

