import { useEffect, useState } from 'react'
import Sidebar from './components/Sidebar.jsx'
import StatCard from './components/StatCard.jsx'
import Topbar from './components/Topbar.jsx'
import Icon from './components/Icon.jsx'
import LoginPage from './components/LoginPage.jsx'
import PatientManagement from './components/PatientManagement.jsx'
import DoctorManagement from './components/DoctorManagement.jsx'
import NurseManagement from './components/NurseManagement.jsx'
import AppointmentManagement from './components/AppointmentManagement.jsx'
import BedManagement from './components/BedManagement.jsx'
import DepartmentManagement from './components/DepartmentManagement.jsx'
import AdminProfilePage from './components/AdminProfilePage.jsx'
import AdminChangePasswordPage from './components/AdminChangePasswordPage.jsx'
import { apiUrl } from './api.js'
import './App.css'

const pageRoutes = {
  Dashboard: '/dashboard',
  Patients: '/patients',
  Doctors: '/doctors',
  Nurses: '/nurses',
  Appointments: '/appointments',
  'Wards & Rooms': '/wards-rooms',
  'Medical Records': '/medical-records',
  Departments: '/departments',
  'My Profile': '/my-profile',
  'Change Password': '/change-password',
}

const knownRoutes = new Set(Object.values(pageRoutes))
const doctorRoutes = new Set(['/doctor-dashboard', ...Object.values(pageRoutes).filter((path) => path !== '/dashboard')])

function pageFromPath(pathname) {
  if (pathname === '/doctor-dashboard') return 'Dashboard'
  return Object.entries(pageRoutes).find(([, path]) => path === pathname)?.[0] ?? 'Dashboard'
}

function routeForPage(page, role) {
  if (role === 'DOCTOR' && page === 'Dashboard') return '/doctor-dashboard'
  return pageRoutes[page] ?? pageRoutes.Dashboard
}

const appointments = [
  ['09:00 AM', 'Emily Johnson', 'Cardiology', 'Dr. Michael Chen', 'EJ', 'purple'],
  ['10:30 AM', 'Robert Williams', 'General Medicine', 'Dr. Sarah Davis', 'RW', 'blue'],
  ['11:15 AM', 'Sophia Martinez', 'Pediatrics', 'Dr. James Wilson', 'SM', 'orange'],
  ['01:00 PM', 'David Brown', 'Orthopedics', 'Dr. Robert Miller', 'DB', 'teal'],
]

const patientTypes = [
  ['Outpatients', '1,842', '66%', 'outpatient'],
  ['Inpatients', '684', '24%', 'inpatient'],
  ['Emergency', '289', '10%', 'emergency'],
]

export function Dashboard({ user, onLogout, onUserUpdated }) {
  const [connection, setConnection] = useState({ status: 'Checking...', message: '' })
  const [sidebarOpen, setSidebarOpen] = useState(false)
  const [sidebarCollapsed, setSidebarCollapsed] = useState(false)
  const [activePage, setActivePage] = useState(() => pageFromPath(window.location.pathname))

  const handleSidebarToggle = () => {
    if (window.innerWidth < 1200) {
      setSidebarOpen((isOpen) => !isOpen)
      return
    }
    setSidebarCollapsed((isCollapsed) => !isCollapsed)
  }

  useEffect(() => {
    fetch(apiUrl('/api/health'))
      .then((response) => {
        if (!response.ok) throw new Error('Backend is unavailable')
        return response.json()
      })
      .then(setConnection)
      .catch(() => setConnection({ status: 'DOWN', message: 'Backend connection failed' }))
  }, [])

  useEffect(() => {
    const syncPageWithBrowser = () => setActivePage(pageFromPath(window.location.pathname))
    window.addEventListener('popstate', syncPageWithBrowser)
    return () => window.removeEventListener('popstate', syncPageWithBrowser)
  }, [])

  const handleNavigate = (page) => {
    const nextRoute = routeForPage(page, user.role)
    if (window.location.pathname !== nextRoute) window.history.pushState({}, '', nextRoute)
    setActivePage(pageFromPath(nextRoute))
  }

  return (
    <div className={`app-shell ${sidebarCollapsed ? 'sidebar-is-collapsed' : ''}`}>
      <Sidebar isOpen={sidebarOpen} collapsed={sidebarCollapsed} activePage={activePage} onClose={() => setSidebarOpen(false)} onLogout={onLogout} onNavigate={handleNavigate} />
      <div className={`dashboard-content ${sidebarCollapsed ? 'sidebar-collapsed' : ''}`}>
        <Topbar connection={connection} user={user} onMenuClick={handleSidebarToggle} onLogout={onLogout} onNavigate={handleNavigate} />
        <main className="dashboard-main">
          {user.role === 'DOCTOR' && !['Dashboard','Appointments'].includes(activePage) ? <AccessDenied page={activePage} /> : activePage === 'My Profile' ? <AdminProfilePage user={user} onUserUpdated={onUserUpdated} onNavigate={handleNavigate} /> : activePage === 'Change Password' ? <AdminChangePasswordPage onNavigate={handleNavigate} /> : activePage === 'Departments' ? <DepartmentManagement /> : activePage === 'Patients' ? <PatientManagement /> : activePage === 'Doctors' ? <DoctorManagement /> : activePage === 'Nurses' ? <NurseManagement /> : activePage === 'Appointments' ? <AppointmentManagement readOnly={user.role==='DOCTOR'} /> : activePage === 'Wards & Rooms' ? <BedManagement /> : activePage === 'Medical Records' ? <MedicalRecordsPlaceholder /> : <>
          <div className="page-heading"><div><p className="demo-label">DEMO DATA · TODAY, OCTOBER 24, 2025</p><h2>Hospital Overview</h2></div><button className="date-button" type="button"><Icon name="calendar" /> Today <span>⌄</span></button></div>

          <section className="stats-grid" aria-label="Demo summary statistics">
            <StatCard label="Total Patients" value="2,815" trend="+12.5%" icon="patients" tone="blue" />
            <StatCard label="Today's Appointments" value="124" trend="+8.2%" icon="appointments" tone="purple" />
            <StatCard label="Available Doctors" value="42" trend="+3.8%" icon="doctors" tone="teal" />
            <StatCard label="Bed Occupancy" value="76%" trend="-2.4%" icon="wards" tone="orange" />
          </section>

          <section className="dashboard-grid">
            <article className="panel appointment-panel">
              <div className="panel-header"><div><h3>Today's Appointments</h3><p>Scheduled patient consultations <span className="demo-pill">DEMO</span></p></div><button className="text-button" type="button">View All <Icon name="arrow" /></button></div>
              <div className="appointment-list">
                {appointments.map(([time, patient, department, doctor, initials, color]) => <div className="appointment-row" key={patient}>
                  <time>{time}</time><div className={`patient-avatar ${color}`}>{initials}</div><div className="patient-info"><strong>{patient}</strong><span>{department}</span></div><div className="doctor-name"><span>with</span>{doctor}</div><span className="confirmed">Confirmed</span><button className="row-more" type="button" aria-label={`More options for ${patient}`}><Icon name="more" /></button>
                </div>)}
              </div>
            </article>

            <article className="panel patient-panel">
              <div className="panel-header"><div><h3>Patient Statistics</h3><p>Patient distribution <span className="demo-pill">DEMO</span></p></div><button className="dots" type="button" aria-label="More patient statistics"><Icon name="more" /></button></div>
              <div className="patient-stat-body"><div className="donut-chart"><div><strong>2,815</strong><span>Total patients</span></div></div><div className="patient-legend">{patientTypes.map(([label, value, percent, tone]) => <div className="legend-row" key={label}><span className={`legend-dot ${tone}`} /><div><strong>{label}</strong><small>{value} patients</small></div><b>{percent}</b></div>)}</div></div>
            </article>

            <article className="panel activity-panel">
              <div className="panel-header"><div><h3>Weekly Appointments</h3><p>Appointment volume by day <span className="demo-pill">DEMO</span></p></div><button className="select-button" type="button">This week <span>⌄</span></button></div>
              <div className="chart"><div className="y-axis"><span>150</span><span>100</span><span>50</span><span>0</span></div><div className="bars">{[['Mon',62],['Tue',76],['Wed',51],['Thu',88],['Fri',69],['Sat',43],['Sun',58]].map(([day, height]) => <div className="bar-wrap" key={day}><div className={`bar ${day === 'Thu' ? 'highlight' : ''}`} style={{ height: `${height}%` }}><span>{day === 'Thu' && '132'}</span></div><small>{day}</small></div>)}</div></div>
            </article>

            <article className="panel quick-panel"><div className="panel-header"><div><h3>Quick Actions</h3><p>Frequently used actions</p></div></div><div className="quick-actions"><button type="button"><span className="quick-icon blue"><Icon name="plus" /></span><span>Add New Patient</span></button><button type="button"><span className="quick-icon purple"><Icon name="appointments" /></span><span>Book Appointment</span></button><button type="button"><span className="quick-icon teal"><Icon name="reports" /></span><span>View Reports</span></button><button type="button"><span className="quick-icon orange"><Icon name="billing" /></span><span>Create Invoice</span></button></div></article>
          </section>
          </>}
        </main>
      </div>
    </div>
  )
}

function MedicalRecordsPlaceholder() {
  return (
    <section className="panel" aria-labelledby="medical-records-title">
      <div className="panel-header">
        <div>
          <p className="demo-label">MEDICAL RECORDS</p>
          <h2 id="medical-records-title">Medical Records</h2>
          <p>This route is ready for the existing Medical Records module.</p>
        </div>
      </div>
    </section>
  )
}

function AccessDenied({ page }) {
  return <section className="panel" aria-labelledby="access-denied-title"><div className="panel-header"><div><p className="demo-label">DOCTOR ACCESS</p><h2 id="access-denied-title">Access Denied</h2><p>You do not have permission to open {page}. Please contact an administrator if you need access.</p></div></div></section>
}

function csrfToken() {
  return document.cookie.split('; ').find((cookie) => cookie.startsWith('XSRF-TOKEN='))?.split('=').slice(1).join('=')
}

function App() {
  const [user, setUser] = useState(null)
  const [checkingSession, setCheckingSession] = useState(true)

  useEffect(() => {
    fetch(apiUrl('/api/auth/me'), { credentials: 'include' })
      .then((response) => response.ok ? response.json() : null)
      .then((authenticatedUser) => {
        if (authenticatedUser?.role === 'DOCTOR' && !doctorRoutes.has(window.location.pathname)) {
          window.history.replaceState({}, '', '/doctor-dashboard')
        } else if (authenticatedUser?.role === 'ADMIN' && !knownRoutes.has(window.location.pathname)) {
          window.history.replaceState({}, '', pageRoutes.Dashboard)
        }
        setUser(authenticatedUser)
      })
      .catch(() => setUser(null))
      .finally(() => setCheckingSession(false))
  }, [])

  const login = async (email, password) => {
    const response = await fetch(apiUrl('/api/auth/login'), {
      method: 'POST', credentials: 'include', headers: { 'Content-Type': 'application/json' }, body: JSON.stringify({ email, password }),
    })
    const body = await response.json().catch(() => ({}))
    if (!response.ok || !['ADMIN', 'DOCTOR'].includes(body.role)) throw new Error('Invalid email or password.')
    window.history.replaceState({}, '', body.role === 'DOCTOR' ? '/doctor-dashboard' : pageRoutes.Dashboard)
    setUser(body)
  }

  const logout = async () => {
    const token = csrfToken()
    try {
      await fetch(apiUrl('/api/auth/logout'), { method: 'POST', credentials: 'include', headers: token ? { 'X-XSRF-TOKEN': token } : {} })
    } finally {
      window.history.replaceState({}, '', '/')
      setUser(null)
    }
  }

  if (checkingSession) return <main className="login-page" aria-label="Checking session" />
  return user ? <Dashboard user={user} onLogout={logout} onUserUpdated={setUser} /> : <LoginPage onLogin={login} />
}

export default App
