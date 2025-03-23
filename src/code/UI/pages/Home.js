import Navbar from "../components/Navbar";

export default function Home() {
  return (
    <div className="p-6">
      <Navbar />
      <h1 className="text-2xl font-bold mt-4">Welcome to Banking App</h1>
      <p>Select an option from the navbar to get started.</p>
    </div>
  );
}
