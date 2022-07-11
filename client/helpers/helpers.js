export const getDataByTitle = (dataObject, title) =>
  dataObject.find((data) => data.title === title)

const ratingMap = [
  { bound: 450, color: '#FF3C5F', title: 'Критично низкий' },
  { bound: 650, color: '#FFC644', title: 'Низкий балл' },
  { bound: 750, color: '#FFEB43', title: 'Средний балл' },
  { bound: 900, color: '#C6F675', title: 'Высокий балл' },
  { bound: 9999, color: '#9AE023', title: 'Максимальный балл' },
]
export const getChartColorByValue = (value) =>
  ratingMap.find((c) => value < c.bound).color

export const getRatingTitleByValue = (value) =>
  ratingMap.find((c) => value < c.bound).title
