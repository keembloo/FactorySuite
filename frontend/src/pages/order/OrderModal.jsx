import { useState, useEffect } from 'react';
import Modal from '@mui/material/Modal';
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import Stack from '@mui/material/Stack';
import Select from '@mui/material/Select';
import MenuItem from '@mui/material/MenuItem';
import InputLabel from '@mui/material/InputLabel';
import FormControl from '@mui/material/FormControl';

const style = {
    position: 'absolute',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    width: 420,
    bgcolor: 'background.paper',
    borderRadius: 2,
    boxShadow: 24,
    p: 4,
};

function OrderModal({ open, setOpen, onSave, customers = [], products = [] }) {

    const [customerId, setCustomerId] = useState('');
    const [productId, setProductId] = useState('');
    const [quantity, setQuantity] = useState('');
    const [orderItemDtos, setOrderItemDtos] = useState([]);     //상품리스트
    const [price, setPrice] = useState('');

    //상품 추가 버튼 클릭시
    const handleAddItem = () => {

       if (!productId || !quantity) {
           alert('상품과 수량을 입력하세요.');
           return;
       }
        // 선택한 상품 찾기
       const product = products.find(p => p.productId === Number(productId));
console.log(" product:", product);

       if (!product) {
           alert("상품 찾기 실패");
           return;
       }

        // 기존에 새상품 추가하기
       setOrderItemDtos(prev => [
           ...prev,
           {
               productId: Number(productId),
               productName: product.productName,
               price: product.price ?? 0,
               quantity: Number(quantity)
           }
       ]);

       setProductId('');
       setQuantity('');
   };


    // 팝업창 열릴때 초기화
    useEffect(() => {
        if (open) {
            setCustomerId('');
            setProductId('');
            setQuantity('');
            setPrice('');
            setOrderItemDtos([]);
        }
    }, [open]);

    // 상품 삭제
    const handleRemoveItem = (index) => {
        setOrderItemDtos(prev => prev.filter((_, i) => i !== index));
    };

    // 팝업 닫기
    const handleClose = () => {
        setOpen(false);
    };

    // 저장 버튼 클릭시
    const handleSave = () => {
        if (!customerId) {
            alert('거래처를 선택하세요.');
            return;
        }

        if (orderItemDtos.length === 0) {
            alert('상품을 하나 이상 추가하세요.');
            return;
        }

        // 부모 컴포넌트 (order)로 데이터 전달
        onSave?.({
            customerId: Number(customerId),
            totalPrice: Number(totalPrice),
                orderItemDtos: orderItemDtos.map(item => ({
                    productId: item.productId,
                    quantity: item.quantity,
                    price: item.price
                }))
        });
        // 초기화
        setOpen(false);
        setCustomerId('');
        setProductId('');
        setQuantity('');
        setOrderItemDtos([]);

    };

useEffect(() => {
    console.log("items 변경됨:", orderItemDtos);
}, [orderItemDtos]);

useEffect(() => {
    console.log("customerId 상태:", customerId);
}, [customerId]);

useEffect(() => {
    console.log("productId 상태:", productId);
}, [productId]);

useEffect(() => {
    console.log("quantity 상태:", quantity);
}, [quantity]);

    // 총 금액 계산
    const totalPrice = orderItemDtos.reduce(
        (sum, orderItemDtos) => sum + orderItemDtos.price * orderItemDtos.quantity,
        0
    );

    return (
        <Modal open={open} onClose={handleClose}>
            <Box sx={style}>


                <Stack spacing={2}>
                    <FormControl fullWidth>
                        <InputLabel>거래처</InputLabel>
                        <Select
                            value={customerId}
                            label="거래처"
                            onChange={(e) => setCustomerId(e.target.value)}
                        >
                            {customers.map(customer => (
                                <MenuItem key={customer.customerId} value={String(customer.customerId)}>
                                    {customer.customerName}
                                </MenuItem>
                            ))}
                        </Select>
                    </FormControl>

                    <FormControl fullWidth>
                       <InputLabel>상품</InputLabel>
                       <Select
                           value={productId}
                           label="상품"
                           onChange={(e) => setProductId(e.target.value)}
                       >
                           {products.map(product => (
                               <MenuItem key={product.productId} value={String(product.productId)}>
                                   {product.productName} ({product.price.toLocaleString()}원)
                               </MenuItem>
                           ))}
                       </Select>
                    </FormControl>

                   <TextField
                       label="수량"
                       type="number"
                       value={quantity}
                       onChange={(e) => setQuantity(e.target.value)}
                       fullWidth
                   />

                   <Button variant="outlined" onClick={handleAddItem}>
                       상품 추가
                   </Button>

                    <div>
                        {orderItemDtos.map((orderItemDtos, index) => (
                            <div key={index}>
                                {orderItemDtos.productName} | {orderItemDtos.quantity}개 | {orderItemDtos.price.toLocaleString()}원
                                <button onClick={() => handleRemoveItem(index)}>삭제</button>
                            </div>
                        ))}
                    </div>
                    <div>총 금액: {totalPrice.toLocaleString()}원</div>

                    <Stack direction="row" spacing={1} justifyContent="flex-end" mt={2}>
                        <Button variant="outlined" onClick={handleClose}>
                            취소
                        </Button>
                        <Button variant="contained" onClick={handleSave}>
                            저장
                        </Button>
                    </Stack>

                </Stack>
            </Box>
        </Modal>
    );
}

export default OrderModal;