import firebase from "firebase/compat/app";
import "firebase/compat/auth";

const firebaseConfig = {
  apiKey: "AIzaSyB1YvGViJjnK29fmbVsh3Pc3OimiwMMpv4",
  authDomain: "findjobproject-96c36.firebaseapp.com",
  projectId: "findjobproject-96c36",
  storageBucket: "findjobproject-96c36.appspot.com",
  messagingSenderId: "581090010990",
  appId: "1:581090010990:web:fbb2e8973afb93508f837f",
  measurementId: "G-Z63638FY6D",
};

// Initialize Firebase
firebase.initializeApp(firebaseConfig);
export default firebase;
