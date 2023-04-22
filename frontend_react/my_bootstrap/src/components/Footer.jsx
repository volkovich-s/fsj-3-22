import React from 'react'

const Footer = ()=>{
  return (
    <div className='container'>
      <footer className='border-top row justify-content-center text-center'>
        <div className='col align-self-center'>
          <img src='img/logo1.png' width={128} height={128}/>
          <p>A&B - надежный друг</p>
        </div>
        <div className='col align-self-center'>
          <h5>Категория</h5>
          <ul className='nav flex-column'>
            <li className='nav-item'>
              <a className='nav-link p-0 text-muted'>Ссылка</a>
            </li>
            <li className='nav-item'>
              <a className='nav-link p-0 text-muted'>Ссылка</a>
            </li>
          </ul>
        </div>
        <div className='col align-self-center'>
            <h5>Категория</h5>
            <ul className='nav flex-column'>
              <li className='nav-item'>
                <a className='nav-link p-0 text-muted'>Ссылка</a>
              </li>
              <li className='nav-item'>
              <a className='nav-link p-0 text-muted'>Ссылка</a>
              </li>
            </ul>
        </div>
      </footer>
    </div>
  )
}

export default Footer