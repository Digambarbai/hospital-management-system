import { useEffect, useRef, useState } from 'react'
import Icon from './Icon.jsx'
import { apiUrl } from '../api.js'

const csrfToken = () => document.cookie.split('; ').find((cookie) => cookie.startsWith('XSRF-TOKEN='))?.split('=').slice(1).join('=')

function ChangePasswordModal({ onClose }) {
  const [form, setForm] = useState({ oldPassword: '', newPassword: '', confirmPassword: '' })
  const [show, setShow] = useState({ oldPassword: false, newPassword: false, confirmPassword: false })
  const [error, setError] = useState('')
  const [success, setSuccess] = useState('')
  const [saving, setSaving] = useState(false)
  const change = ({ target: { name, value } }) => { setForm((current) => ({ ...current, [name]: value })); setError(''); setSuccess('') }
  const submit = async (event) => {
    event.preventDefault()
    if (!form.oldPassword || !form.newPassword || !form.confirmPassword) return setError('Complete all password fields.')
    if (form.newPassword.length < 6) return setError('New password must be at least 6 characters.')
    if (form.newPassword !== form.confirmPassword) return setError('New password and confirmation do not match.')
    setSaving(true)
    try {
      const token = csrfToken()
      const response = await fetch(apiUrl('/api/auth/change-password'), { method: 'POST', credentials: 'include', headers: { 'Content-Type': 'application/json', ...(token ? { 'X-XSRF-TOKEN': token } : {}) }, body: JSON.stringify(form) })
      const body = await response.json().catch(() => ({}))
      if (!response.ok) throw new Error(body.message || 'Unable to change password.')
      setForm({ oldPassword: '', newPassword: '', confirmPassword: '' })
      setSuccess('Password changed successfully.')
    } catch (requestError) { setError(requestError.message) } finally { setSaving(false) }
  }
  const field = (name, label) => <label className="change-password-field">{label}<div><input name={name} type={show[name] ? 'text' : 'password'} value={form[name]} onChange={change} autoComplete="new-password" /><button type="button" aria-label={`${show[name] ? 'Hide' : 'Show'} ${label}`} onClick={() => setShow((current) => ({ ...current, [name]: !current[name] }))}><svg viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"><path d="M2 12s3.5-6 10-6 10 6 10 6-3.5 6-10 6S2 12 2 12Z"/><circle cx="12" cy="12" r="2.5"/></svg></button></div></label>
  return <div className="change-password-overlay" role="presentation" onMouseDown={(event) => event.target === event.currentTarget && onClose()}><form className="change-password-modal" role="dialog" aria-modal="true" aria-label="Change Password" onSubmit={submit}><div className="change-password-header"><div><span>ACCOUNT SECURITY</span><h2>Change Password</h2></div><button type="button" aria-label="Close dialog" onClick={onClose}>×</button></div><p className="change-password-intro">Verify your current password before setting a new one.</p>{field('oldPassword', 'Old Password')}{field('newPassword', 'New Password')}{field('confirmPassword', 'Confirm New Password')}{error && <p className="change-password-feedback error" role="alert">{error}</p>}{success && <p className="change-password-feedback success" role="status">{success}</p>}<div className="change-password-actions"><button type="button" className="profile-modal-cancel" onClick={onClose}>Cancel</button><button type="submit" className="profile-modal-save" disabled={saving}>{saving ? 'Saving…' : 'Save New Password'}</button></div></form></div>
}

function Topbar({ connection, user, onMenuClick, onLogout, onNavigate }) {
  const isUp = connection.status === 'UP'
  const isDoctor = user.role === 'DOCTOR'
  const initials = user.name.split(' ').map((part) => part[0]).join('').slice(0, 2)
  const [profileOpen, setProfileOpen] = useState(false)
  const [changePasswordOpen, setChangePasswordOpen] = useState(false)
  const profileRef = useRef(null)

  useEffect(() => {
    const closeOnOutsideClick = (event) => {
      if (profileRef.current && !profileRef.current.contains(event.target)) setProfileOpen(false)
    }
    const closeOnEscape = (event) => {
      if (event.key === 'Escape') setProfileOpen(false)
    }
    document.addEventListener('pointerdown', closeOnOutsideClick)
    document.addEventListener('keydown', closeOnEscape)
    return () => {
      document.removeEventListener('pointerdown', closeOnOutsideClick)
      document.removeEventListener('keydown', closeOnEscape)
    }
  }, [])

  return (
    <header className="topbar">
      <div className="topbar-left">
        <button className="sidebar-toggle" type="button" onClick={onMenuClick} aria-label="Toggle navigation"><Icon name="menu" /></button>
        <div><p className="eyebrow">{isDoctor ? 'DOCTOR OVERVIEW' : 'ADMIN OVERVIEW'}</p><h1>Welcome back, {user.name}</h1></div>
      </div>
      <div className="topbar-actions">
        <div className="online-status d-none d-md-flex" title={connection.message}>
          <span className={`status-dot ${isUp ? 'online' : ''}`} /> {isUp ? 'Online' : 'Offline'}
        </div>
        <div className="profile-menu" ref={profileRef}>
          <button className="profile-trigger" type="button" aria-label="Open profile menu" aria-expanded={profileOpen} onClick={() => setProfileOpen((open) => !open)}>
            <div className="profile-avatar">{initials}</div>
            <div className="profile-copy d-none d-md-block"><strong>{user.name}</strong><small>{isDoctor ? 'Doctor' : user.email}</small></div>
            <span className="profile-chevron">⌄</span>
          </button>
          {profileOpen && <div className="profile-dropdown" role="menu">
            <div className="profile-dropdown-heading"><span className="profile-dropdown-label">{isDoctor ? 'DOCTOR ACCOUNT' : 'ACCOUNT'}</span><strong>{user.name}</strong><small>{isDoctor ? `Doctor · ${user.email}` : user.email}</small></div>
            {!isDoctor && <><button type="button" role="menuitem" onClick={() => { setProfileOpen(false); onNavigate('My Profile') }}>My Profile</button>
            <button type="button" role="menuitem" onClick={() => { setProfileOpen(false); setChangePasswordOpen(false); onNavigate('Change Password') }}>Change Password</button></>}
            <button className="profile-logout" type="button" role="menuitem" onClick={onLogout}>Logout</button>
          </div>}
        </div>
      </div>
      {changePasswordOpen && <ChangePasswordModal onClose={() => setChangePasswordOpen(false)} />}
    </header>
  )
}

export default Topbar
