import './Footer.css';
import Container from 'react-bootstrap/Container';
import Navbar from 'react-bootstrap/Navbar';
import Nav from 'react-bootstrap/Nav';
import FooterLink from './Footer/FooterLink';

export default ()=>{
  return (
    <footer>
      <Navbar className='Footer p-0'>
        <Container>
          <Navbar.Brand className='Brand text-start'>
            <img
              className='BrandImage'
              width='128px' height='128px'
              src='/images/footer/logo.png'
              alt='/images/footer/logo.png'
            />
            <div className='BrandText'>
              BRIKTOUR -<br/>выбор качества
            </div>
          </Navbar.Brand>
          <Nav>
            <FooterLink image='/images/footer/link_1.png'/>
            <FooterLink image='/images/footer/link_2.png'/>
            <FooterLink image='/images/footer/link_1.png'/>
            <FooterLink image='/images/footer/link_2.png'/>
          </Nav>
        </Container>
      </Navbar>
    </footer>
  );
};