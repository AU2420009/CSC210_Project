"use client";

import { useState, useEffect } from "react";
import { getProgress } from "@/lib/progress";
import { modules } from "@/data/modules";

export default function ProgressDashboard() {
  const [stats, setStats] = useState({
    totalLessons: 0,
    completedLessons: 0,
    averageScore: 0,
    completedModules: 0,
  });

  useEffect(() => {
    const progress = getProgress();
    const totalLessons = modules.reduce((acc, mod) => acc + mod.lessons.length, 0);
    const completedLessons = progress.completedLessons.length;
    
    const scores = Object.values(progress.quizScores);
    const averageScore = scores.length > 0 
      ? Math.round(scores.reduce((a, b) => a + b, 0) / scores.length)
      : 0;

    const completedModules = modules.filter(mod => 
      mod.lessons.every(lesson => progress.completedLessons.includes(lesson.id))
    ).length;

    setStats({
      totalLessons,
      completedLessons,
      averageScore,
      completedModules,
    });
  }, []);

  const progressPercentage = stats.totalLessons > 0 
    ? Math.round((stats.completedLessons / stats.totalLessons) * 100)
    : 0;

  return (
    <div className="bg-gradient-to-br from-blue-500 to-indigo-600 rounded-2xl p-8 text-white shadow-xl">
      <h2 className="text-2xl font-bold mb-6">Your Progress</h2>
      
      <div className="grid grid-cols-2 md:grid-cols-4 gap-4 mb-6">
        <div className="bg-white/10 backdrop-blur-sm rounded-xl p-4">
          <div className="text-3xl font-bold mb-1">{stats.completedLessons}</div>
          <div className="text-sm text-blue-100">Lessons Completed</div>
        </div>
        
        <div className="bg-white/10 backdrop-blur-sm rounded-xl p-4">
          <div className="text-3xl font-bold mb-1">{progressPercentage}%</div>
          <div className="text-sm text-blue-100">Overall Progress</div>
        </div>
        
        <div className="bg-white/10 backdrop-blur-sm rounded-xl p-4">
          <div className="text-3xl font-bold mb-1">{stats.averageScore}%</div>
          <div className="text-sm text-blue-100">Average Score</div>
        </div>
        
        <div className="bg-white/10 backdrop-blur-sm rounded-xl p-4">
          <div className="text-3xl font-bold mb-1">{stats.completedModules}</div>
          <div className="text-sm text-blue-100">Modules Done</div>
        </div>
      </div>

      <div className="space-y-2">
        <div className="flex items-center justify-between text-sm">
          <span>Course Completion</span>
          <span className="font-semibold">{stats.completedLessons} / {stats.totalLessons}</span>
        </div>
        <div className="w-full bg-white/20 rounded-full h-3 overflow-hidden">
          <div 
            className="bg-white h-full rounded-full transition-all duration-500"
            style={{ width: `${progressPercentage}%` }}
          />
        </div>
      </div>
    </div>
  );
}
