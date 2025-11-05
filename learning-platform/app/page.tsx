"use client";

import ModuleCard from "@/components/ModuleCard";
import ProgressDashboard from "@/components/ProgressDashboard";
import { modules } from "@/data/modules";
import Link from "next/link";

export default function Home() {
  return (
    <div className="min-h-screen bg-gradient-to-br from-zinc-50 to-zinc-100">
      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
        <header className="mb-12 text-center">
          <h1 className="text-5xl font-bold text-zinc-900 mb-4">
            Data Structures Learning Platform
          </h1>
          <p className="text-xl text-zinc-600 max-w-2xl mx-auto">
            Master essential data structures through interactive lessons, code examples, and quizzes
          </p>
        </header>

        <div className="mb-12">
          <ProgressDashboard />
        </div>

        <section>
          <div className="flex items-center justify-between mb-6">
            <h2 className="text-3xl font-bold text-zinc-900">Learning Modules</h2>
            <span className="text-sm text-zinc-500">{modules.length} modules available</span>
          </div>
          
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
            {modules.map((module) => (
              <ModuleCard key={module.id} module={module} />
            ))}
          </div>
        </section>

        <footer className="mt-16 pt-8 border-t border-zinc-200 text-center text-zinc-500 text-sm">
          <p>Built with Next.js, TypeScript, and Tailwind CSS</p>
          <p className="mt-2">Based on the Library Management System project</p>
        </footer>
      </div>
    </div>
  );
}
