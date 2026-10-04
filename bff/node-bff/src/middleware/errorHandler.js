const errorHandler = (err, req, res, next) => {
  console.error(`[BFF Error] ${req.method} ${req.originalUrl} -`, err.message);

  if (err.response) {
    const status = err.response.status || 500;
    const errorData = err.response.data || {
      type: 'https://store.io/errors/downstream-error',
      title: 'Downstream Service Error',
      status: status,
      detail: err.message,
      instance: req.originalUrl,
      timestamp: new Date().toISOString()
    };
    return res.status(status).json(errorData);
  }

  return res.status(500).json({
    type: 'https://store.io/errors/bff-internal-error',
    title: 'Internal Server Error',
    status: 500,
    detail: err.message || 'An unexpected error occurred in BFF gateway',
    instance: req.originalUrl,
    timestamp: new Date().toISOString()
  });
};

module.exports = errorHandler;
