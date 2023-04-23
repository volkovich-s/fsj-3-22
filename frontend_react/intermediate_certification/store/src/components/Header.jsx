import './Header.css';
import Container from 'react-bootstrap/esm/Container';
import Navbar from 'react-bootstrap/Navbar';
import Nav from 'react-bootstrap/Nav';

export default ()=>{
  return (
    <header>
      <Navbar className='Header fixed-top'>
        <Container>
          <Navbar.Brand className='Brand'>BRIKTOUR</Navbar.Brand>
          <Nav>
            <Nav.Link className='Link'>Корзина</Nav.Link>
            <Nav.Link className='Link'>Избранное</Nav.Link>
            <Nav.Link className='Link'>О нас</Nav.Link>
          </Nav>
        </Container>
      </Navbar>
    </header>
  );
};