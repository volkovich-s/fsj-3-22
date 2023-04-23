import './Card.css';
import Button from 'react-bootstrap/Button';
import Card from 'react-bootstrap/Card';

export default (props)=>{
  return (
      <Card className='ProductCard'>
        <Card.Body>
          <Card.Title className='ProductCardTitle'>{props.title}</Card.Title>
          <img
            width='80%'
            src={props.image}
            alt={props.image}
          />
          <Card.Text className='ProductCardPrice'>цена: {props.price}</Card.Text>
          <Button className='ProductCardButton'>Забронировать</Button>
        </Card.Body>
      </Card>
  );
};