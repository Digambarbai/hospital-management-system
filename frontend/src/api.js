// Configure the backend origin at build time. The localhost fallback is only
// used during local Vite development; Netlify must set VITE_API_BASE_URL.
const configuredOrigin = (import.meta.env.VITE_API_BASE_URL || '').replace(/\/$/, '')
const developmentOrigin = import.meta.env.DEV ? 'http://localhost:8080' : ''

export const apiUrl = (path) => `${configuredOrigin || developmentOrigin}${path}`
