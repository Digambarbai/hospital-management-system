import { useState } from 'react'
import './LoginPage.css'

const initialForm = { identifier: '', password: '', remember: false }

function FieldError({ children }) {
  return children ? <p className="login-field-error" role="alert">{children}</p> : null
}

function LoginPage({ onLogin }) {
  const [form, setForm] = useState(initialForm)
  const [errors, setErrors] = useState({})
  const [showPassword, setShowPassword] = useState(false)
  const [loading, setLoading] = useState(false)
  const [formMessage, setFormMessage] = useState('')

  const updateField = (event) => {
    const { name, value, checked, type } = event.target
    setForm((current) => ({ ...current, [name]: type === 'checkbox' ? checked : value }))
    setErrors((current) => ({ ...current, [name]: '' }))
    setFormMessage('')
  }

  const validate = () => {
    const nextErrors = {}
    if (!form.identifier.trim()) nextErrors.identifier = 'Enter your email address or username.'
    if (!form.password) nextErrors.password = 'Enter your password.'
    return nextErrors
  }

  const handleSubmit = async (event) => {
    event.preventDefault()
    const nextErrors = validate()
    setErrors(nextErrors)
    if (Object.keys(nextErrors).length) return

    setLoading(true)
    try {
      await onLogin(form.identifier.trim(), form.password)
    } catch (error) {
      setFormMessage(error.message || 'Invalid email or password.')
    } finally {
      setLoading(false)
    }
  }

  return (
    <main className="login-page">
      <section className="login-brand-panel">
        <div className="login-brand-content">
          <div className="login-brand"><span className="login-brand-mark">+</span><div><strong>MediCare</strong><small>HOSPITAL MANAGEMENT SYSTEM</small></div></div>
          <div className="login-hero-copy"><span className="login-kicker">HEALTHCARE, CONNECTED</span><h1>Care that feels <em>closer.</em></h1><p>One thoughtful workspace for the people who keep care moving forward.</p></div>
          <div className="medical-illustration" aria-hidden="true"><div className="illustration-glow" /><div className="illustration-card illustration-chart"><span /><span /><span /><span /><span /></div><div className="illustration-card illustration-pulse"><i>⌁</i><div><b>24/7</b><small>Patient care</small></div></div><div className="illustration-doctor"><div className="doctor-head" /><div className="doctor-body"><span className="doctor-cross">+</span></div></div><div className="illustration-ring ring-one" /><div className="illustration-ring ring-two" /></div>
          <p className="login-copyright">© 2025 MediCare Hospital System. All rights reserved.</p>
        </div>
      </section>

      <section className="login-form-panel">
        <div className="login-mobile-brand"><span className="login-brand-mark">+</span><strong>MediCare</strong></div>
        <div className="login-card">
          <div className="login-heading"><p>ADMIN PORTAL · WELCOME BACK</p><h2>Sign in to your account</h2><span>Use your authorized administrator email and password.</span></div>
          <form noValidate onSubmit={handleSubmit}>
            <div className="login-form-group"><label htmlFor="identifier">Admin Email</label><div className={`login-input-wrap ${errors.identifier ? 'has-error' : ''}`}><span aria-hidden="true">✉</span><input id="identifier" name="identifier" type="email" autoComplete="username" placeholder="admin@example.com" value={form.identifier} onChange={updateField} /></div><FieldError>{errors.identifier}</FieldError></div>
            <div className="login-form-group"><div className="login-label-row"><label htmlFor="password">Password</label><button type="button" className="forgot-link">Forgot password?</button></div><div className={`login-input-wrap ${errors.password ? 'has-error' : ''}`}><span aria-hidden="true"><svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"><rect x="4" y="10" width="16" height="10" rx="2"/><path d="M8 10V7a4 4 0 0 1 8 0v3"/></svg></span><input id="password" name="password" type={showPassword ? 'text' : 'password'} autoComplete="current-password" placeholder="Enter your password" value={form.password} onChange={updateField} /><button className="password-toggle" type="button" onClick={() => setShowPassword((visible) => !visible)} aria-label={showPassword ? 'Hide password' : 'Show password'}>{showPassword ? 'Hide' : 'Show'}</button></div><FieldError>{errors.password}</FieldError></div>
            <label className="remember-control"><input name="remember" type="checkbox" checked={form.remember} onChange={updateField} /><span>Remember me on this device</span></label>
            {formMessage && <div className="login-message" role="status">{formMessage}</div>}
            <button className="signin-button" type="submit" disabled={loading}>{loading ? <><span className="login-spinner" /> Signing in…</> : 'Sign In'}</button>
          </form>
          <p className="demo-disclaimer">Authorized administrators only. Your session is secured by the backend.</p>
          <p className="login-help">Need help accessing your account? <a href="mailto:support@medicare.example">Contact support</a></p>
        </div>
        <p className="login-mobile-copyright">© 2025 MediCare Hospital System</p>
      </section>
    </main>
  )
}

export default LoginPage
