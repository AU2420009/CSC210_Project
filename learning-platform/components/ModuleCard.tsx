"use client";

import { Module } from "@/types";
import { getModuleProgress } from "@/lib/progress";
import { useState, useEffect } from "react";
import Link from "next/link";

interface ModuleCardProps {
  module: Module;
}

export default function ModuleCard({ module }: ModuleCardProps) {
  const [progress, setProgress] = useState(0);

  useEffect(() => {
    setProgress(getModuleProgress(module.id, module.lessons.length));
  }, [module.id, module.lessons.length]);

  return (
    <Link href={`/module/${module.id}`}>
      <div className="group relative overflow-hidden rounded-2xl border border-zinc-200 bg-white p-6 transition-all duration-300 hover:shadow-xl hover:scale-[1.02] cursor-pointer">
        <div className="flex items-start justify-between mb-4">
          <div className="text-4xl mb-2">{module.icon}</div>
          <span className="text-sm font-medium text-zinc-500">{module.estimatedTime}</span>
        </div>
        
        <h3 className="text-xl font-bold text-zinc-900 mb-2 group-hover:text-blue-600 transition-colors">
          {module.title}
        </h3>
        
        <p className="text-zinc-600 text-sm mb-4 line-clamp-2">
          {module.description}
        </p>

        <div className="space-y-2">
          <div className="flex items-center justify-between text-sm">
            <span className="text-zinc-500">{module.lessons.length} lessons</span>
            <span className="font-semibold text-blue-600">{progress}%</span>
          </div>
          
          <div className="w-full bg-zinc-100 rounded-full h-2 overflow-hidden">
            <div 
              className="bg-gradient-to-r from-blue-500 to-blue-600 h-full rounded-full transition-all duration-500"
              style={{ width: `${progress}%` }}
            />
          </div>
        </div>

        {progress === 100 && (
          <div className="absolute top-4 right-4 bg-green-500 text-white text-xs font-bold px-2 py-1 rounded-full">
            ✓ Complete
          </div>
        )}
      </div>
    </Link>
  );
}
