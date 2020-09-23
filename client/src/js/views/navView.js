import { elements } from './base';

export const updateGreeting = name => {
  elements.greeting.innerHTML = `Welcome, ${name}!`;
};
