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

function CustomerModal({ open, setOpen, onSave , data }) {
    const [form, setForm] = useState({
        customerId: '',
        customerName: '',
        phone: '',
        address: '',
    });

    useEffect(() => {
        if (data) { // 수정 시: 넘어온 데이터로 셋팅
            setForm({
                customerId: data.customerId,
                customerName: data.customerName,
                phone: data.phone,
                address: data.address,
            });
        } else {
            setForm({ customerId: '', customerName: '', phone: '', address: '' }); // 등록 시: 초기화
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
        if (!form.customerName.trim()) {
            alert('거래처 이름을 입력하세요.');
            return;
        }

        onSave?.(form);   // 부모에게 데이터 전달
        setOpen(false);

        // 입력값 초기화
        setForm({
            customerName: '',
            phone: '',
            address: '',
        });
    };

    return (
        <Modal open={open} onClose={handleClose}>
            <Box sx={style}>
                <Typography variant="h6" mb={3}>
                    {data ? '거래처 정보 수정' : '거래처 신규 등록'}
                </Typography>

                <Stack spacing={2}>
                    <TextField
                        label="거래처 이름"
                        name="customerName"
                        value={form.customerName}
                        onChange={handleChange}
                        fullWidth
                    />

                    <TextField
                        label="전화번호"
                        name="phone"
                        value={form.phone}
                        onChange={handleChange}
                        fullWidth
                    />

                    <TextField
                        label="주소"
                        name="address"
                        value={form.address}
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

export default CustomerModal;