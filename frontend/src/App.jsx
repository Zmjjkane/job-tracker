import { BrowserRouter, Routes, Route} from "react-router-dom";
import JobListPage from "./pages/JobListPage";

export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<JobListPage />} />
      </Routes>
    </BrowserRouter>
  );
}