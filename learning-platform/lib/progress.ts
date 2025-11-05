"use client";

import { UserProgress } from "@/types";

const STORAGE_KEY = "learning-platform-progress";

export const getProgress = (): UserProgress => {
  if (typeof window === "undefined") {
    return {
      completedLessons: [],
      quizScores: {},
    };
  }

  const stored = localStorage.getItem(STORAGE_KEY);
  if (stored) {
    return JSON.parse(stored);
  }

  return {
    completedLessons: [],
    quizScores: {},
  };
};

export const saveProgress = (progress: UserProgress): void => {
  if (typeof window !== "undefined") {
    localStorage.setItem(STORAGE_KEY, JSON.stringify(progress));
  }
};

export const markLessonComplete = (lessonId: string): void => {
  const progress = getProgress();
  if (!progress.completedLessons.includes(lessonId)) {
    progress.completedLessons.push(lessonId);
    saveProgress(progress);
  }
};

export const saveQuizScore = (lessonId: string, score: number): void => {
  const progress = getProgress();
  progress.quizScores[lessonId] = score;
  saveProgress(progress);
};

export const isLessonComplete = (lessonId: string): boolean => {
  const progress = getProgress();
  return progress.completedLessons.includes(lessonId);
};

export const getModuleProgress = (moduleId: string, totalLessons: number): number => {
  const progress = getProgress();
  const completedInModule = progress.completedLessons.filter(id => 
    id.startsWith(moduleId)
  ).length;
  return Math.round((completedInModule / totalLessons) * 100);
};

export const resetProgress = (): void => {
  if (typeof window !== "undefined") {
    localStorage.removeItem(STORAGE_KEY);
  }
};
