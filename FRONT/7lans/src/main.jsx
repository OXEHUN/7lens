import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App";
import { Provider } from "react-redux";
import store, { persistor } from "./store/store";
import { BrowserRouter } from "react-router-dom";
// import "bootstrap/dist/css/bootstrap.min.css";
import { PersistGate } from "redux-persist/integration/react";
// import "./scss/main.scss";


ReactDOM.createRoot(document.getElementById("root")).render(
  <Provider store={store}>
    <PersistGate loading={null} persistor={persistor}>
      <BrowserRouter>
        <App />
      </BrowserRouter>
    </PersistGate>
  </Provider>
);
