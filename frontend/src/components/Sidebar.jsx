import Icon from './Icon.jsx'

const mainNavigation = [
  ['dashboard', 'Dashboard'], ['doctors', 'Doctors'], ['patients', 'Patients'],
  ['nurses', 'Nurses'], ['appointments', 'Appointments'], ['wards', 'Wards & Rooms'],
  ['departments', 'Departments'],
]

function NavigationGroup({ title, items, active, collapsed, onNavigate }) {
  return (
    <div className="nav-group">
      <p className="nav-label">{title}</p>
      {items.map(([icon, label]) => (
        <button key={label} className={`sidebar-link ${active === label ? 'active' : ''}`} type="button" title={collapsed ? label : undefined} data-tooltip={collapsed ? label : undefined} onClick={() => onNavigate(label)}>
          <Icon name={icon} /> <span>{label}</span>
        </button>
      ))}
    </div>
  )
}

function Sidebar({ isOpen, collapsed, activePage, onClose, onLogout, onNavigate }) {
  const closeAfterNavigation = (label) => {
    if (window.innerWidth < 1200) onClose()
    if (label) onNavigate(label)
  }

  const handleLogout = () => {
    closeAfterNavigation()
    onLogout()
  }

  return (
    <>
      {isOpen && <button className="sidebar-backdrop" type="button" aria-label="Close navigation" onClick={onClose} />}
      <aside className={`sidebar ${isOpen ? 'open' : ''} ${collapsed ? 'collapsed' : ''}`}>
        <div className="brand">
          <div className="brand-mark">+</div>
          <div><strong>MediCare</strong><span>HOSPITAL SYSTEM</span></div>
          <button className="sidebar-close d-lg-none" type="button" onClick={onClose}><Icon name="close" /></button>
        </div>
        <nav>
          <NavigationGroup title="MAIN MENU" items={mainNavigation} active={activePage} collapsed={collapsed} onNavigate={closeAfterNavigation} />
        </nav>
        <div className="sidebar-bottom">
          <button className="sidebar-link logout-sidebar-link" type="button" title={collapsed ? 'Log out' : undefined} data-tooltip={collapsed ? 'Log out' : undefined} onClick={handleLogout}><Icon name="arrow" /> <span>Log out</span></button>
        </div>
      </aside>
    </>
  )
}

export default Sidebar
