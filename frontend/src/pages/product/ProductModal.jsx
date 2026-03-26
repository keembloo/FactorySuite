import { useState, useEffect } from 'react';
import Modal from '@mui/material/Modal';
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import Stack from '@mui/material/Stack';

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

function ProductModal({ open, setOpen, onSave , data }) {
  const [form, setForm] = useState({
    productId: '',
    productName: '',
    price: '',
    category: '',
    forSale: '',
  });

  useEffect(() => {
      if (data) { // 수정 시: 넘어온 데이터로 셋팅
        setForm({
            productId: data.productId,
            productName: data.productName,
            price: data.price,
            category: data.category,
            forSale: data.forSale
        });
      } else {
        setForm({ productId: '', productName: '', price: '', category: '' , forSale: '' }); // 등록 시: 초기화
      }
    }, [data, open]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm(prev => ({ ...prev, [name]: value }));
  };

  const handleClose = () => {
    setOpen(false);
  };

  const handleSave = () => {
    if (!form.productName.trim()) {
      alert('제품 이름을 입력하세요.');
      return;
    }

    onSave?.(form);   // 부모에게 데이터 전달
    setOpen(false);

    // 입력값 초기화
    setForm({
      productName: '',
      price: '',
      category: '',
      forSale: ''
    });
  };

  return (
    <Modal open={open} onClose={handleClose}>
      <Box sx={style}>
        <Typography variant="h6" mb={3}>
          {data ? '제품 정보 수정' : '제품 신규 등록'}
        </Typography>

        <Stack spacing={2}>
          <TextField
            label="제품 이름"
            name="productName"
            value={form.productName}
            onChange={handleChange}
            fullWidth
          />

          <TextField
            label="카테고리"
            name="category"
            value={form.category}
            onChange={handleChange}
            fullWidth
          />

          <TextField
            label="가격"
            name="price"
            value={form.price}
            onChange={handleChange}
            fullWidth
          />
        <select  label="판매유무"
                            name="forSale"

                                        onChange={handleChange}>
            <option value="Y">판매중</option>
            <option value="N">판매종료</option>
        </select>
         <TextField
            label="판매유무"
            name="forSale"
            value={form.forSale}
            onChange={handleChange}
            fullWidth
          />

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

export default ProductModal;