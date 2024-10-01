// Получаем элементы поля пароля и иконки
const passwordInput = document.getElementById('password');
const togglePassword = document.getElementById('togglePassword');

// Добавляем обработчик клика для переключения видимости пароля
togglePassword.addEventListener('click', function () {
    // Проверяем текущий тип поля
    const type = passwordInput.getAttribute('type') === 'password' ? 'text' : 'password';
    // Меняем тип поля
    passwordInput.setAttribute('type', type);
    // Меняем символ иконки в зависимости от состояния
    this.textContent = type === 'password' ? '👀️' : '🙈';  // Глаз или закрытый глаз
});