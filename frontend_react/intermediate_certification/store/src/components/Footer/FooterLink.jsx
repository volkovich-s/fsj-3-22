import Nav from 'react-bootstrap/Nav';

export default (props)=>{
  return (
    <Nav.Link className='Link'>
      <img
        className='BrandImage'
        width='64px' height='64px'
        src={props.image}
        alt={props.image}
      />
    </Nav.Link>
  );
};